package Semester_2;
import java.util.Scanner;
    /*
     OOP
     Class = Blueprint to make an object. Needs a Constructor to build the object, and Super calls parent constructor. Then attributes, which are the nouns/ variables,
     and finally Methods, which are the verbs.
     Object = a grouping of code in an entity
     Different from primitive variables (int, double, boolean, etc)
     Ex.
     Animal
     poop();
     die();
     sleep();
     mate();
     eat();
     You can make different Animals
     Inheritance = Class structure where child classes inherit attributes and methods from parent class
     Keyword: Extends
     Hamster
     runOnWheel()
     And all of the other animal methods
     Polymorphism = customize methods for different classes. Overrides methods if method is rewritten in child object
      */
    public class RPGRunner {
        public static Boolean gameOn = true;
//Static Methods
        public static void main(String[] args) {
            //Character Creation
            Hero hero = new Hero();
            Scanner scann = new Scanner(System.in);
            System.out.println("Enter a name!");
            hero.name = scann.nextLine();
            //Game Welcome
            System.out.println("   ____  _                            ____                  _   \n" +
                    "  / __ \\| |                          / __ \\                | |  \n" +
                    " | |  | | |__   __ _ _ __ ___   __ _| |  | |_   _  ___  ___| |_ \n" +
                    " | |  | | '_ \\ / _` | '_ ` _ \\ / _` | |  | | | | |/ _ \\/ __| __|\n" +
                    " | |__| | |_) | (_| | | | | | | (_| | |__| | |_| |  __/\\__ \\ |_ \n" +
                    "  \\____/|_.__/ \\__,_|_| |_| |_|\\__,_|\\___\\_\\\\__,_|\\___||___/\\__|");
            System.out.println();
            System.out.println("Welcome to ObamaQuest, " + hero.name + "!");
            while (gameOn) {
                mainMenuRunner(hero);
            }
        }

        public static void mainMenuRunner(Hero h) {
            Scanner input = new Scanner(System.in);
            System.out.println("Menu:");
            System.out.println("a. Move");
            System.out.println("b. Get Hero Info");
            System.out.println("c. Use Item");
            System.out.println("d. Fight End Boss");
            System.out.println("What would you like to do?");
            String myChoice = input.nextLine().toLowerCase();

            switch (myChoice) {
                case "a":
                    move(h);
                    break;
                case "b":
                    System.out.println(h.toString());
                    break;
                case "c":
                    for (Item items : h.itemBag) {
                        System.out.println(items.name + "(" + items.itemUses + ")");
                    }
                    System.out.println("Which item would you like to use? \n 'a' for Potion, 'b' for Shield Buff, 'c' for Full Restore, or anything else to return to the Main Menu");
                    String itemChoice = input.nextLine().toLowerCase();
                    if (itemChoice.equals("a") && h.item1.itemUses > 0) {
                        h.item1.useItem(h);
                    } else if (itemChoice.equals("b") && h.item2.itemUses > 0) {
                        h.item2.useItem(h);
                    } else if (itemChoice.equals("c") && h.item3.itemUses > 0) {
                        h.item3.useItem(h);
                    } else if (itemChoice.equals("a") && h.item1.itemUses == 0) {
                        System.out.println("You don't have any Health Potions to use!");
                    } else if (itemChoice.equals("b") && h.item2.itemUses == 0) {
                        System.out.println("You don't have any Shield Buffs to use!");
                    } else if (itemChoice.equals("c") && h.item3.itemUses == 0) {
                        System.out.println("You don't have any Full Restores to use!");
                    } else {
                        break;
                    }
                    break;
                case "d":
                    obama(h);
                    break;
                default:
                    System.out.println("That was not a valid choice.");
            }

        }

        public static void move(Hero h) {
            int randomChance = (int) (Math.random() * 100);
            if (randomChance >= 95) {
                int fallDMG = (int) ((Math.random() * 24) + 1);
                h.reduceHealth(fallDMG);
                System.out.println("You fell into a pit and took " + fallDMG + " damage!");
            } else if (randomChance > 40) {
                h.findItem();
            } else if (randomChance > 10) {
                battle(h);
            } else if (randomChance > 2) {
                h.item1.itemUses -= 1;
                h.item2.itemUses -= 1;
                if (h.item1.itemUses < 0) {
                    h.item1.itemUses = 0;
                }
                if (h.item2.itemUses < 0) {
                    h.item2.itemUses = 0;
                }
                System.out.println("Obama's henchmen ambush you and steal one of your Potions and one of your Shield Buffs!");
            } else {
                h.health = 0;
                System.out.println("The spectre of Obama suddenly appears before you and catches you off guard. Before you can respond, he unleashes his Super Obama Attack and kills you!");
                gameOn = false;
            }
        }

        public static void battle(Hero h) {
            Enemy enemy1 = new Enemy();
            int enemyName = (int) (Math.random() * 10);
            if (enemyName >= 7) {
                enemy1.type = "Jomal";
                enemy1.canFly = false;
                enemy1.attackPower = 20;
            } else if (enemyName >= 4) {
                enemy1.type = "Mr. Goulet";
                enemy1.canFly = false;
                enemy1.attackPower = 30;
            } else if (enemyName >= 2) {
                enemy1.type = "Lil Wayne";
                enemy1.canFly = true;
                enemy1.attackPower = 40;
            } else {
                enemy1.type = "Jeff Bezos";
                enemy1.canFly = true;
                enemy1.attackPower = 25;
            }
            boolean playerTurn = true;
            int run = 0;
            boolean attacked = false;
            System.out.println("A wild " + enemy1.type + " appears before you, challenging you to mortal combat!");

            //Game Loop
            Scanner scan = new Scanner(System.in);
            while (h.getHP() > 0 && enemy1.getHP() > 0 && run != 1) {
                playerTurn = true;
                //Player Turn
                while (playerTurn) {
                    System.out.println("What do you do? 'A' to Attack, 'B' to Defend, 'C' to use an Item, or 'D' to run");
                    String action = scan.nextLine().toLowerCase();
                    switch (action) {
                        case "a":
                            if (enemy1.isFlying) {
                                h.flyingAttack(enemy1);
                            } else {
                                h.attack(enemy1);
                            }
                            System.out.println("You attack the enemy");
                            attacked = true;
                            playerTurn = false;
                            break;
                        case "b":
                            h.defend();
                            if (h.shieldUp = true) {
                                System.out.println("You raise your shield in defense");
                                playerTurn = false;
                            }
                            break;
                        case "c":
                            for (Item items : h.itemBag) {
                                System.out.println(items.name + "(" + items.itemUses + ")");
                            }
                            System.out.println("Which item would you like to use? \n 'a' for Potion, 'b' for Shield Buff, 'c' for Full Restore, or anything else to return to the Main Menu");
                            String itemChoice = scan.nextLine().toLowerCase();
                            if (itemChoice.equals("a") && h.item1.itemUses > 0) {
                                h.item1.useItem(h);
                                playerTurn = false;
                            } else if (itemChoice.equals("b") && h.item2.itemUses > 0) {
                                h.item2.useItem(h);
                                playerTurn = false;
                            } else if (itemChoice.equals("c") && h.item3.itemUses > 0) {
                                h.item3.useItem(h);
                                playerTurn = false;
                            } else if (itemChoice.equals("a") && h.item1.itemUses == 0) {
                                System.out.println("You don't have any Health Potions to use!");
                            } else if (itemChoice.equals("b") && h.item2.itemUses == 0) {
                                System.out.println("You don't have any Shield Buffs to use!");
                            } else if (itemChoice.equals("c") && h.item3.itemUses == 0) {
                                System.out.println("You don't have any Full Restores to use!");
                            }
                            break;
                        case "d":
                            int runChance = (int) (Math.random() * 2);
                            if (runChance == 1) {
                                System.out.println("You tried to run, but " + enemy1.type + " prevents you from escaping!");
                                playerTurn = false;
                            } else {
                                playerTurn = false;
                                run = 1;
                            }
                            break;
                        default:
                            System.out.println("Enter an acceptable response.");
                            break;
                    }
                }

                if (h.getHP() <= 0) {
                    System.out.println("YOU DIED");
                    gameOn = false;
                } else if (enemy1.getHP() <= 0) {
                    System.out.println("VICTORY ACHIEVED");
                } else if (run == 1) {
                    System.out.println("You fled " + enemy1.type);
                }
                scan.nextLine();

                //Enemy Turn----------------------------------------------------------------------------------
                if (h.getHP() > 0 && enemy1.getHP() > 0 && run != 1) {
                        int enemyMove = (int) (Math.random() * 10);
                        if (enemy1.canFly) {
                            if (enemyMove >= 6) {
                                if (h.shieldUp) {
                                    enemy1.shieldAttack(h);
                                } else {
                                    enemy1.attack(h);
                                }
                                System.out.println(enemy1.type + " has attacked you!");
                            } else if (enemyMove >= 3 && attacked) {
                                enemy1.parry(h);
                                System.out.println(enemy1.type + " has parried your attack, inflicting the same amount of damage you dealt to him!");
                            } else if (enemyMove > 3) {
                                if (h.shieldUp) {
                                    enemy1.shieldAttack(h);
                                } else {
                                    enemy1.attack(h);
                                }
                                System.out.println(enemy1.type + " has attacked you!");
                            } else {
                                enemy1.fly();
                            }
                        } else {
                            if (enemyMove >= 4) {
                                if (h.shieldUp) {
                                    enemy1.shieldAttack(h);
                                    h.shieldUp = false;
                                } else {
                                    enemy1.attack(h);
                                }
                                System.out.println(enemy1.type + " has attacked you!");
                            } else if (attacked) {
                                enemy1.parry(h);
                                System.out.println(enemy1.type + " has parried your attack, inflicting the same amount of damage you dealt to him!");
                            } else {
                                if (h.shieldUp) {
                                    enemy1.shieldAttack(h);
                                    h.shieldUp = false;
                                } else {
                                    enemy1.attack(h);
                                }
                                System.out.println(enemy1.type + " has attacked you!");
                            }
                        }
                        playerTurn = true;
                        attacked = false;
                }
            }
                    if (h.getHP() <= 0) {
                        System.out.println("YOU DIED");
                        gameOn = false;
                    }
        }

        public static void obama(Hero h) {
            Boss obama = new Boss();
            boolean playerTurn = true;
            boolean attacked = false;
            System.out.println(obama.type + ", the 44th President of the U.S.A, accepts your challenge!");
            Scanner scan = new Scanner(System.in);
            scan.nextLine();
            System.out.println("Obama: 'Prepare to die!'");
            System.out.println();
            //Player Turn
            while (h.getHP() > 0 && obama.getHP() > 0) {
                while (playerTurn) {
                    System.out.println("What do you do? 'A' to Attack, 'B' to Defend, 'C' to use an Item, or 'D' to run");
                    String action = scan.nextLine().toLowerCase();
                    switch (action) {
                        case "a":
                            h.flyingAttack(obama);
                            System.out.println("You attack the enemy");
                            playerTurn = false;
                            attacked = true;
                            break;
                        case "b":
                            h.defend();
                            if (h.shieldUp = true) {
                                System.out.println("You raise your shield in defense");
                                playerTurn = false;
                            }
                            break;
                        case "c":
                            for (Item items : h.itemBag) {
                                System.out.println(items.name + "(" + items.itemUses + ")");
                            }
                            System.out.println("Which item would you like to use? \n 'a' for Potion, 'b' for Shield Buff, 'c' for Full Restore, or anything else to return to the Main Menu");
                            String itemChoice = scan.nextLine().toLowerCase();
                            if (itemChoice.equals("a") && h.item1.itemUses > 0) {
                                h.item1.useItem(h);
                                playerTurn = false;
                            } else if (itemChoice.equals("b") && h.item2.itemUses > 0) {
                                h.item2.useItem(h);
                                playerTurn = false;
                            } else if (itemChoice.equals("c") && h.item3.itemUses > 0) {
                                h.item3.useItem(h);
                                playerTurn = false;
                            } else if (itemChoice.equals("a") && h.item1.itemUses == 0) {
                                System.out.println("You don't have any Health Potions to use!");
                            } else if (itemChoice.equals("b") && h.item2.itemUses == 0) {
                                System.out.println("You don't have any Shield Buffs to use!");
                            } else if (itemChoice.equals("c") && h.item3.itemUses == 0) {
                                System.out.println("You don't have any Full Restores to use!");
                            }
                            break;
                        case "d":
                            int runChance = (int) (Math.random());
                            if (runChance == 0) {
                                System.out.println("There is no escaping Obama!");
                            }
                            break;
                        default:
                            System.out.println("Enter an acceptable response.");
                            break;
                    }
                }
                if (h.getHP() <= 0){
                    System.out.println("Obama has vanquished you!");
                    gameOn = false;
                }
                if (obama.getHP() <= 0){
                    System.out.println("Obama has been defeated! Victory Royale!");
                    gameOn = false;
                }
                scan.nextLine();
                //Obama Turn
                if (h.getHP() > 0 && obama.getHP() > 0) {
                    int obamaMove = (int) (Math.random() * 10);
                    if (obama.charge == 5) {
                        if (h.shieldUp) {
                            h.health = 1;
                            System.out.println("Your shield lets you barely survive Obama's deadly laser!");
                        } else {
                            obama.superAttack(h);
                        }
                    } else if (obamaMove >= 7 && obama.charge < 5) {
                        obama.superAttackPrep();
                    } else if (obamaMove >= 4) {
                        if (h.defend())
                            if (h.shieldUp) {
                                obama.shieldAttack(h);
                            } else {
                                obama.attack(h);
                            }
                        System.out.println("Obama launches a vicious attack on you!");
                    } else if (obamaMove > 2) {
                        obama.heal();
                    } else {
                        obama.parry(h);
                        System.out.println("Obama parries you, inflicting your own damage upon yourself.");
                    }
                    playerTurn = true;
                    attacked = false;
                }
            }
            if (h.getHP() <= 0){
                System.out.println("Obama has vanquished you!");
                gameOn = false;
            }
        }
//Character Family
        static class Character {
            //Attributes
            int health;
            int attackPower;

            //Default Constructor
            Character() {
                health = 100;
                attackPower = 25;
            }

            void attack(Character c) {
                c.reduceHealth(attackPower);
            }

            void reduceHealth(int ap) {
                health -= ap;
            }

            int getHP() {
                return health;
            }




        }

        static class Hero extends Character {
            String name;
            boolean shieldUp;
            int shieldPower;
            HealthPotion item1 = new HealthPotion();
            ShieldBuff item2 = new ShieldBuff();
            FullRestore item3 = new FullRestore();
            Item[] itemBag = {item1, item2, item3};

            Hero() {
                super();
                shieldUp = true;
                shieldPower = 10;
            }

            public void attack(Character c) {
                //Num from 1 to 100
                int r1 = (int) (Math.random() * 100 + 1);
                if (r1 <= 5) {
                    //CRITICAL HIT
                    c.reduceHealth(c.health);
                    System.out.println("YOU LANDED A CRITICAL BLOW!");
                } else {
                    int r2 = (int) (Math.random() * 10 + 20);
                    c.reduceHealth(r2);
                    System.out.println("You hit the enemy for "
                            + r2 + " damage.");

                }
            }

            public void flyingAttack(Character c) {
                //Num from 1 to 100
                int r1 = (int) (Math.random() * 100 + 1);
                if (r1 <= 10) {
                    //CRITICAL HIT
                    c.reduceHealth(c.health);
                    System.out.println("YOU LANDED A CRITICAL BLOW!");
                } else {
                    int r2 = (int) (Math.random() * 5 + 10);
                    c.reduceHealth(r2);
                    System.out.println("You hit the enemy for "
                            + r2 + " damage.");

                }
            }

            public boolean defend() {
                return shieldUp;
            }

            public void findItem() {
                int itemChance = (int) (Math.random() * 100);
                if (itemChance >= 60) {
                    System.out.println("You found a Health Potion!");
                    item1.itemUses++;
                } else if (itemChance > 5) {
                    System.out.println("You found a Shield Buff!");
                    item2.itemUses++;
                } else {
                    System.out.println("You found a Full Restore!");
                    item3.itemUses++;
                }
            }
            public String toString() {
                return name +" has " + health + " health remaining, " + shieldPower + "shield power, and an average attack power of 25";
            }
        }

        static class Enemy extends Character {
            String type;
            boolean canFly;
            boolean isFlying;

            Enemy() {
                super();
                isFlying = false;
            }

            public void parry(Hero h) {
                h.reduceHealth((h.attackPower));
            }

            public void fly() {
                if (canFly && !isFlying) {
                    isFlying = true;
                    System.out.println(type + " is now flying and will therefore take half damage!");
                }
            }

            void shieldAttack(Hero h) {
                int red = attackPower - h.shieldPower;
                if (red < 10){
                    h.reduceHealth(10);
                }
                else {
                    h.reduceHealth(red);
                }
            }
        }

        static class Boss extends Enemy {
            boolean canSuperAttack;
            int charge;

            Boss() {
                type = "BARACK HUSSEIN OBAMA II";
                canSuperAttack = false;
                canFly = true;
                isFlying = true;
                attackPower = 45;
                health = 300;
                charge = 0;
            }

            public void heal() {
                System.out.println("With the power of the Affordable Care Act, Obama heals himself!");
                health += 50;
                if (health > 300) {
                    health = 300;
                }
            }

            public void superAttackPrep() {
                System.out.println("Obama is charging his presidential power!");
                charge += 1;
                if (charge == 5) {
                    System.out.println("Obama has reached peak power!");
                    canSuperAttack = true;
                }
            }

            public void superAttack(Hero h) {
                if (canSuperAttack) {
                    System.out.println("Obama releases the power of 44 presicdencies and immediately obliterates you!");
                    h.health = 0;
                }
            }
        }
//Item Family
        static class Item {
            int itemUses;
            String name;

            Item() {
                itemUses = 0;
            }
        }

        static class HealthPotion extends Item {
            int restoreValue;

            HealthPotion() {
                super();
                name = "HEALTH POTION";
                restoreValue = (int) ((Math.random() * 30) + 30);
            }

            public void useItem(Hero h) {
                h.health += restoreValue;
                itemUses -= 1;
                if (h.health > 100) {
                    h.health = 100;
                }
                System.out.println("You used a Health Potion and healed " + restoreValue + " health!");
            }
        }

        static class FullRestore extends HealthPotion {
            FullRestore() {
                super();
                name = "FULL RESTORE";
            }

            public void useItem(Hero h) {
                h.health = 100;
                itemUses -= 1;
                System.out.println("The Full Restore returned your health to 100!");
            }
        }

        static class ShieldBuff extends Item {
            int buffValue;

            ShieldBuff() {
                super();
                name = "SHIELD BUFF";
                buffValue = (int) ((Math.random() * 20) + 30);
            }

            public void useItem(Hero h) {
                h.shieldPower += buffValue;
                itemUses -= 1;
                System.out.println("The Shield Buff increase your shield absorption by " + buffValue + "!");
            }
        }
    }



