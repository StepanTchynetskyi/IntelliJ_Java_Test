package com.steven;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Hero Balanar = new Hero("balanar",4,350,6);
        Hero Archer = new Hero("Archer",2,250,7,false);
        Hero Linter = new Hero("Linter",6,500,3);
        Hero Keiry = new Hero("Keiry",8,700,4,false);

        List<Hero> heroes = new ArrayList<>();
        heroes.add(Balanar);
        heroes.add(Archer);
        heroes.add(Linter);
        heroes.add(Keiry);

        Weapon simpleSword = new Weapon("Sword",100,3,3);
        Weapon superbow = new Weapon("Super bow",600,7,6,false);
        Weapon longSword = new Weapon("Long sword",300,9,4);
        Weapon longBow = new Weapon("Long bow",250,4,6,false);
        List<Weapon> meleeWeapons = new ArrayList<>();
        meleeWeapons.add(simpleSword);
        meleeWeapons.add(longSword);
        List<Weapon> rangeWeapons = new ArrayList<>();
        rangeWeapons.add(superbow);
        rangeWeapons.add(longBow);

        Equipment cuirass = new Equipment("Cuirass",7,400);
        Equipment cloak = new Equipment("Cloak",3,200);
        Equipment shadowCuirass = new Equipment("Shadow Cuirass",9,550);
        Equipment beastCloak = new Equipment("Beast Cloak",6,350);

        List<Equipment> equipments = new ArrayList<>();
        equipments.add(cuirass);
        equipments.add(cloak);
        equipments.add(shadowCuirass);
        equipments.add(beastCloak);

        Enemy brocker = new Enemy("Brocker",7,7);
        Enemy moner = new Enemy("Moner",6,8);
        Enemy keeper = new Enemy("Keeper",9,9);
        Enemy joker = new Enemy("Joker",7,7);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(brocker);
        enemies.add(moner);
        enemies.add(keeper);
        enemies.add(joker);

        Scanner scan = new Scanner(System.in);
        String start="";

        while(!start.toLowerCase().equals("play")){
            System.out.println("Write PLAY to start game\nWrite Exit to exit game");
            start = scan.next();
            if(start.toLowerCase().equals("exit")){
                System.exit(0);
            }
            if(start.toLowerCase().equals("play")){
                break;
            }
            System.out.println("Wrong input");
        }

        System.out.print("Enter your name: ");
        String name = scan.next();
        User player1 = new User(name);

        System.out.print("Pick hero(write number counting from 0): \n");
        for (Hero hero:heroes) {
            System.out.println(hero.toString());
        }
        int getType = scan.nextByte();
        player1.setBalance(player1.getBalance()-heroes.get(getType).getHeroCost());
        player1.setHero(heroes.get(getType));
        System.out.println(player1.getBalance());

        System.out.print("Pick weapon(write number counting from 0): \n");
        while(true) {
            if (player1.getHero().getTypeOfAttack()) {
                for (Weapon weapon : meleeWeapons) {
                    System.out.println(weapon.toString());
                }
                getType = scan.nextByte();
                if(player1.getBalance() - meleeWeapons.get(getType).getWeaponCost() >= 0){
                    player1.setBalance(player1.getBalance() - meleeWeapons.get(getType).getWeaponCost());
                    break;
                }else System.out.println("It's too expensive for you get another weapon except "+ getType);
            } else {
                for (Weapon weapon : rangeWeapons) {
                    System.out.println(weapon);
                }
                getType = scan.nextByte();
                if(player1.getBalance() - rangeWeapons.get(getType).getWeaponCost() >= 0){
                    player1.setBalance(player1.getBalance() - rangeWeapons.get(getType).getWeaponCost());
                    break;
                }else System.out.println("It's too expensive for you get another weapon except "+ getType);
            }



        }
        if(player1.getHero().getTypeOfAttack())player1.setWeapon(meleeWeapons.get(getType));
        else player1.setWeapon(rangeWeapons.get(getType));
        System.out.println(player1.getBalance());

        System.out.print("Pick equipment(write number counting from 0): \n");
        while(true) {
            for (Equipment equipment : equipments) {
                System.out.println(equipment.toString());
            }
            getType = scan.nextByte();
            if(player1.getBalance()-equipments.get(getType).getEquipmentCost()>=0) {
                player1.setBalance(player1.getBalance() - equipments.get(getType).getEquipmentCost());
                break;
            }else System.out.println("It's too expensive for you get another equipment except "+ getType);
        }
        player1.setEquipment(equipments.get(getType));
        System.out.println(player1.getBalance());

        System.out.println("Your Hero:\n");
        System.out.println(player1.toString());

        int damage = player1.getHero().getForce()*player1.getWeapon().getWeaponDamage();
        System.out.println("Your damage: "+ damage);
        int randomEnemy= (int)(Math.random()*4);
        Enemy myEnemy= enemies.get(randomEnemy);
        System.out.println(myEnemy.toString());
        int criticalDamage;
        while(true){
            criticalDamage = (int)(Math.random()*player1.getWeapon().getWeaponRange());
            myEnemy.setHealth(myEnemy.getHealth()-(damage+criticalDamage - myEnemy.getEnemyDefence()));
            System.out.println(myEnemy.getEnemyName()+" health: "+myEnemy.getHealth());
            if(myEnemy.getHealth()<=0) {
                System.out.println("You win");
                break;
            }
            //Thread.sleep(1000);
            if(myEnemy.isMelee()) criticalDamage = (int)(Math.random()*12);
            else criticalDamage = (int)(Math.random()*10);
            System.out.println("critical damage :" + criticalDamage);
            player1.getHero().setHealth(player1.getHero().getHealth()-
                    (myEnemy.getEnemyForce()*2+criticalDamage-
                            (player1.getHero().getHeroDefence()+player1.getEquipment().getDefence())));
            System.out.println("Your health: " + player1.getHero().getHealth());
            if(player1.getHero().getHealth()<0){
                System.out.println("You lost");
                break;
            }
            //Thread.sleep(1000);

        }
        System.out.println("Game over");

    }
}
