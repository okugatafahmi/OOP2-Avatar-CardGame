# OOP2-Avatar-CardGame
Tugas Besar 2 IF2210 OOP
## IF2210 Project Template

Here is a project using gradle as the build tools.
To run this project, you can run these commands (for linux):
```
./gradlew run
```
or (for windows):
```
gradlew.bat
```

It will start the main function in this app.
For this app, the main function lives in `AvatarDuel.java`.

You can explore more about gradle [here](https://guides.gradle.org/creating-new-gradle-builds/)

### Credit

All images and description are taken from [Avatar Wikia](https://avatar.fandom.com/wiki/Avatar_Wiki)

## How To Play
1. Player will be change to play each other.
2. There will be 4 phase for each player, `DRAW`, `MAIN`, `BATTLE`, `END` phases.
3. In `DRAW` phase, player must draw card from deck. Player can simply click the card deck.
4. In `MAIN` phase, player can summon character or skill card and also use land card. First choose what card player want. If it is land card, click again to it. If it is character card, click to field where it want to summoned. Character field is field at the first row. If it is skill card, click to character card which player want to attach. In `MAIN` phase, player can also to choose player's character's card `STANCE` in the field by `click` the character's field player want to do it. It will change the character card's mode (`ATTACK` or `DEFENSE`). player can also throw player's card field by `right click` the card. If player has done things in this phase, click the `Done MAIN phase` button.
5. In `BATTLE` phase, player can attack enemy player. `Click` character card that player want to choose attack (character card must be in `ATTACK` mode). Player can't choose new summoned character card. After click the card, click the enemy character card field which want to be attacked. The target card must have total `STANCE` value which less than current choosed player's character card. If the enemy doesn't have any character card in field, player can simply click any enemy's field to make damage to enemy. If player has done things in this phase, click the `Done BATTLE phase` button.
6. In `END` phase, player can't do anything and must change turn with other player

## Gameplay Visual
![Set Name](/img/set_name.png)
![Gameplay](/img/gameplay.png)
## Contributor
Kelompok 7 K-1
Nama | NIM
------------ | -------------
Okugata Fahmi Nurul Y. F. | 13519031
Made Prisha Wulansari | 13518049
Muhammad Cisco Zulfikar | 13518073
Muhammad Ridwan Fauzi | 13518124
Hanif Muhamad Gana | 13518127
Dwiani Yulia Ariyanti | 13518142
