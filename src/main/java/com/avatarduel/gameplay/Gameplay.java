package com.avatarduel.gameplay;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

import com.avatarduel.model.card.Element;
import com.avatarduel.model.card.Land;
import com.avatarduel.model.card.Skill;
import com.avatarduel.model.card.Aura;
import com.avatarduel.model.card.PowerUp;
import com.avatarduel.model.card.Destroy;
import com.avatarduel.model.card.Summonedable;
import com.avatarduel.model.field.CharacterField;
import com.avatarduel.model.field.FieldPos;
import com.avatarduel.model.field.Stance;
import com.avatarduel.model.field.Field.Type;
import com.avatarduel.model.player.DeckCardEmpty;
import com.avatarduel.controller.PlayerController;
import com.avatarduel.model.card.Card;
import com.avatarduel.model.card.CardFactory;
import com.avatarduel.model.card.Character;
import com.avatarduel.util.CSVReader;
import com.avatarduel.view.field.CharacterFieldView;
import com.avatarduel.view.field.SkillFieldView;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Kelas Gameplay yang bertanggung jawab dalam permainan
 */
public class Gameplay implements Subject, GlobalField {
    private static final String CARD_CSV_FOLDER_PATH = "../card/data/";
    private LinkedList<Card> listCard;
    private PlayerController[] playerControllers;
    private GameState gameState;
    private StackPane hoverSpace;
    private Text status;
    private static CharacterFieldView fieldViewSrc;

    public Gameplay(PlayerController[] playerControllers) {
        this.playerControllers = playerControllers;
        this.hoverSpace = new StackPane();
        this.hoverSpace.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.hoverSpace.setMinSize(250, 360);
        this.hoverSpace.setMaxSize(250, 360);

        this.status = new Text();
        this.status.setText("Loading...");
    }

    /**
     * @return the status
     */
    public Text getStatus() {
        return status;
    }

    /**
     * @return the hoverSpace
     */
    public StackPane getHoverSpace() {
        return hoverSpace;
    }

    /**
     * Load cards
     * 
     * @throws IOException        if the file is not found
     * @throws URISyntaxException if this URL is not formatted strictly according to
     *                            to RFC2396 and cannot be converted to a URI.
     */
    public void loadCards() throws IOException, URISyntaxException {
        File folder = new File(getClass().getResource(CARD_CSV_FOLDER_PATH).toURI());
        LinkedList<Card> loaded = new LinkedList<>();
        CardFactory cardFactory = new CardFactory();
        for (File file : folder.listFiles()) {
            String filename = file.getName().split("[.]")[0];
            CSVReader reader = new CSVReader(file, "\t");
            reader.setSkipHeader(true);
            List<String[]> rows = reader.read();
            for (String[] row : rows) {
                Card card = cardFactory.getCard(filename);
                card.setName(row[1]);
                card.setElement(Element.valueOf(row[2]));
                card.setDescription(row[3]);
                card.setImagePath(getClass().getResource(row[4]).toString());

                if (filename.equals("character")) {
                    ((Character) card).setPower(Integer.parseInt(row[5]));
                    ((Character) card).setAttack(Integer.parseInt(row[6]));
                    ((Character) card).setDefense(Integer.parseInt(row[7]));
                } else if (filename.equals("aura")) {
                    ((Aura) card).setPower(Integer.parseInt(row[5]));
                    ((Aura) card).setAttack(Integer.parseInt(row[6]));
                    ((Aura) card).setDefense(Integer.parseInt(row[7]));
                } else if (filename.equals("destroy")) {
                    ((Destroy) card).setPower(Integer.parseInt(row[5]));
                } else if (filename.equals("powerup")) {
                    ((PowerUp) card).setPower(Integer.parseInt(row[5]));
                }
                loaded.add(card);
            }
        }
        listCard = loaded;
    }

    /**
     * Return player's deck
     * 
     * @param nCard maximal card in deck
     * @return stack of card which represent the deck
     */
    public Stack<Card> setDeck(int nCard) {
        Stack<Card> deck = new Stack<>();
        List<Card> listCard;
        listCard = this.listCard.stream().filter(card -> card instanceof Character).collect(Collectors.toList());
        Collections.shuffle(listCard);
        listCard.subList(0, Math.round((float) nCard * 2 / 5)).stream().forEach(card -> deck.push(card));
        listCard = this.listCard.stream().filter(card -> card instanceof Land).collect(Collectors.toList());
        Collections.shuffle(listCard);
        listCard.subList(0, Math.round((float) nCard * 2 / 5)).stream().forEach(card -> deck.push(card));
        listCard = this.listCard.stream().filter(card -> card instanceof Skill).collect(Collectors.toList());
        Collections.shuffle(listCard);
        listCard.subList(0, Math.round((float) nCard / 5)).stream().forEach(card -> deck.push(card));
        Collections.shuffle(deck);
        return deck;
    }

    /**
     * Run the game
     */
    public void run() {
        for (int i = 0; i < 2; ++i) {
            playerControllers[i].setSubject(this);
            playerControllers[i].setGlobalField(this);
            playerControllers[i].setDeck(this.setDeck(playerControllers[i].getTotalDeckCard()));
            playerControllers[i].setDeckCardHover(hoverSpace);
            // TODO bikin click event
            final int id = i;
            playerControllers[i].setFieldClickHandler(e -> {
                if (this.gameState.getPhase() == Phase.MAIN) {
                    int fieldColumn = ((CharacterFieldView) e.getSource()).getColumn();
                    // Menaruh kartu di field
                    if (PlayerController.getCardToBeMove() != null) {
                        if (this.gameState.getTurn() == id) {
                            if (PlayerController.getCardToBeMove().getCard() instanceof Character) {
                                playerControllers[id].summonCharacterCard(PlayerController.getCardToBeMove(),
                                        fieldColumn);
                            } else if (PlayerController.getCardToBeMove().getCard() instanceof Skill) {
                                // Attach skill ke character sendiri)
                                playerControllers[id].summonSkillCard(PlayerController.getCardToBeMove(),
                                        new FieldPos(id, fieldColumn));
                            }
                        } else {
                            if (PlayerController.getCardToBeMove().getCard() instanceof Skill) {
                                // Attach skill (ini skill ke character musuh)
                                playerControllers[(id + 1) % 2].summonSkillCard(PlayerController.getCardToBeMove(),
                                        new FieldPos(id, fieldColumn));
                            }
                        }
                    } else if (this.gameState.getTurn() == id) {
                        if (e.getButton() == MouseButton.SECONDARY) {
                            playerControllers[id].removeCardAtField(Type.CHARACTER, fieldColumn);
                        } else {
                            playerControllers[id].changeStance(fieldColumn);
                        }
                    }
                } else if (this.gameState.getPhase() == Phase.BATTLE) {
                    // TODO Battle Phase
                    FieldPos fieldPos = new FieldPos(id, ((CharacterFieldView) e.getSource()).getColumn());
                    // kalau belum ada kartu asal
                    if (fieldViewSrc == null) {
                        if (this.gameState.getTurn() == id) {
                            CharacterField field = this.playerControllers[id].getCharacterField(fieldPos.getColumn());
                            if (field.getCard() != null && field.getCurrentStance() == Stance.ATTACK) {
                                if (field.getHasAttacked()) {
                                    showErrorAlert("Selected character has attacked!");
                                } else if (field.getFirstSummon()) {
                                    showErrorAlert("Can't attack with just summoned character card!");
                                } else {
                                    fieldViewSrc = ((CharacterFieldView) e.getSource());
                                    fieldViewSrc.setBorder(Color.RED);
                                }
                            }
                        }
                    }
                    // kalau pencet field sendiri
                    else if (this.gameState.getTurn() == id) {
                        // cancel menyerang
                        if (fieldViewSrc == ((CharacterFieldView) e.getSource())) {
                            fieldViewSrc.setBorder(Color.BLACK);
                            fieldViewSrc = null;
                        }
                        // ganti kartu
                        else {
                            CharacterField field = this.playerControllers[id].getCharacterField(fieldPos.getColumn());
                            if (field.getCard() != null && field.getCurrentStance() == Stance.ATTACK) {
                                if (field.getHasAttacked()) {
                                    showErrorAlert("Selected character card has attacked!");
                                } else {
                                    fieldViewSrc.setBorder(Color.BLACK);
                                    fieldViewSrc = ((CharacterFieldView) e.getSource());
                                    fieldViewSrc.setBorder(Color.RED);
                                }
                            }
                        }
                    }
                    // kalau pencet field musuh
                    else {
                        battle(fieldViewSrc, ((CharacterFieldView) e.getSource()));
                    }

                }
            }, e -> {
                if (this.gameState.equals(Phase.MAIN, id)) {
                    int fieldColumn = ((SkillFieldView) e.getSource()).getColumn();
                    if (e.getButton() == MouseButton.SECONDARY) {
                        playerControllers[id].removeCardAtField(Type.SKILL, fieldColumn);
                    }

                }
            });
            playerControllers[i].firstDrawCard();
        }
        this.gameState = new GameState();
        notifyObserver();
    }

    public void battle(CharacterFieldView cFieldViewSrc, CharacterFieldView cFieldViewDest) {
        int idSrc = gameState.getTurn();
        int idDest = (gameState.getTurn() + 1) % 2;
        CharacterField fieldSrc = this.playerControllers[idSrc].getCharacterField(cFieldViewSrc.getColumn());
        CharacterField fieldDest = this.playerControllers[idDest].getCharacterField(cFieldViewDest.getColumn());
        // kondisi yang mungkin tidak terjadi
        if (fieldSrc.getCard() == null || fieldSrc.getHasAttacked() || fieldSrc.getCurrentStance() != Stance.ATTACK) {
            return;
        }

        boolean canAttack = false;
        if (fieldDest.getCard() == null) {
            if (this.playerControllers[idDest].getTotalCharacterInField() == 0) {
                this.playerControllers[idDest].getDamage(fieldSrc.getStanceValue());
                canAttack = true;
            } else {
                showErrorAlert("Can't attack enemy directly because enemy still has card(s)");
            }
        } else {
            int srcAttack = fieldSrc.getStanceValue();
            int destStancValue = fieldDest.getStanceValue();
            if (srcAttack > destStancValue) {
                if (fieldDest.getCurrentStance() == Stance.ATTACK || fieldDest.getHasPowerUp()) {
                    this.playerControllers[idDest].getDamage(srcAttack - destStancValue);
                }
                this.playerControllers[idDest].removeCardAtField(Type.CHARACTER, fieldDest.getFieldPos().getColumn());
                canAttack = true;
            } else {
                showErrorAlert("Can't attack character card which has " + fieldDest.getCurrentStance()
                        + " attribute higher or equal with selected character card's attack!");
            }
        }
        if (canAttack) {
            fieldSrc.setHasAttacked(true);
            // TODO hapus. hanya utk debug
            // System.out.println(this.playerControllers[idSrc].player);
            // System.out.println(this.playerControllers[idDest].player);
            fieldViewSrc.setBorder(Color.BLACK);
            fieldViewSrc = null;
            checkWinner();
        }
    }

    private void showErrorAlert(String msg) {
        Alert alert = new Alert(AlertType.ERROR, msg, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    private void showWinnerAlert(int player) {
        this.gameState.setFinish(player);

        Alert alert = new Alert(AlertType.INFORMATION, "Congratulation, player " + (player+1) + " win the game!!!", ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    public void checkWinner() {
        if (this.playerControllers[0].getHp()==0 ) {
            this.gameState.setFinish(1);
            notifyObserver();
        }
        else if (this.playerControllers[1].getHp()==0) {
            this.gameState.setFinish(0);
            notifyObserver();
        }
    }

    @Override
    public GameState getUpdate() {
        return this.gameState;
    }

    @Override
    public void update() {
        if (fieldViewSrc != null) {
            fieldViewSrc.setBorder(Color.BLACK);
            fieldViewSrc = null;
        }
        this.gameState.next();
        notifyObserver();
    }

    @Override
    public void notifyObserver() {
        this.status.setText(this.gameState.toString());
        try {
            for (Observer observer : this.playerControllers) {
                observer.update();
            }
            if (this.gameState.getPhase() == Phase.FINISHED) {
                showWinnerAlert(this.gameState.getTurn());
            }
        }
        catch (DeckCardEmpty err) {
            this.gameState.setFinish((err.getId()+1)%2);
            notifyObserver();
        }
    }

    @Override
    public Summonedable getCardAtField(Type type, FieldPos fieldPos) {
        return playerControllers[fieldPos.getPlayer()].getCardAtField(type, fieldPos.getColumn());
    }

    @Override
    public void removeCardAtField(Type type, FieldPos fieldPos) {
        playerControllers[fieldPos.getPlayer()].removeCardAtField(type, fieldPos.getColumn());
    }

    @Override
    public void attachSkill(FieldPos skillPos, FieldPos fieldPos) {
        playerControllers[fieldPos.getPlayer()].attachSkill(skillPos, fieldPos.getColumn());
    }

    @Override
    public void removeSkillOfCharacterAtField(FieldPos skillPos, FieldPos fieldPos) {
        playerControllers[fieldPos.getPlayer()].removeSkillOfCharacterAtField(skillPos, fieldPos.getColumn());
    }
}