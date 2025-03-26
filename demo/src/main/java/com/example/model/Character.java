package com.example.model;

//Attributes for main character

public class Character {

    protected int strength;
    protected int dexterity; 
    protected int constitution; 
    protected int intellegence; 
    protected int wisdom; 
    protected int charisma; 
    
    protected int currentHp; 
    protected int maxHp;  
    protected int speed; 
    protected int initiative; 

    protected CharacterClass characterClass; 
    protected int lvl;
    protected int xp;
    protected String name; 


    public int getArmorClass() {
        return dexterity / 2 + 5;
    }
}
 