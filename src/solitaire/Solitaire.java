package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		for(CardNode prev=deckRear,curr=deckRear.next;curr!=deckRear;prev=curr,curr=curr.next){
			
			if(curr.cardValue==27&&curr.next==deckRear){
				CardNode temp1=curr;
				CardNode temp2=deckRear.next;
				prev.next=deckRear;
				deckRear=temp1;
				temp1=temp2;
				deckRear=temp1;
				return;
			}
			if(curr.next.cardValue==27&&curr.next==deckRear){
				CardNode temp1=deckRear;
				CardNode temp2=deckRear.next.next;
				curr.next=deckRear.next;
				curr.next.next=temp1;
				temp1.next=temp2;
				deckRear=curr.next;
				return;
			}
			if(curr.cardValue==27&&curr.next!=deckRear){
				CardNode temp1=curr;
				CardNode temp2=curr.next.next;
				prev.next=curr.next;
				prev.next.next=temp1;
				temp1.next=temp2;
				return;
			}
		}
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
		for(CardNode prev=deckRear,curr=deckRear.next;curr!=deckRear;prev=curr,curr=curr.next){
			if(curr.next.next==deckRear&&curr.cardValue==28){
				CardNode temp1=curr;
				CardNode temp2=deckRear.next;
				prev.next=curr.next;
				deckRear.next=temp1;
				temp1.next=temp2;
				deckRear=temp1;
				return;
			}
			if(curr.next==deckRear&&curr.cardValue==28){
				CardNode temp1=curr;
				CardNode head=deckRear.next;
				CardNode temp2=head.next;
				prev.next=deckRear;
				head.next=temp1;
				temp1.next=temp2;
				deckRear=head;
				return;
			}
			if(curr.next==deckRear&&curr.next.cardValue==28){
				CardNode temp1=deckRear;
				CardNode temp2=deckRear.next.next;
				CardNode temp3=temp2.next;
				curr.next=deckRear.next;
				temp2.next=temp1;
				temp1.next=temp3;
				deckRear=curr.next;
				return;
			}
			if(curr.cardValue==28){
				CardNode temp1=curr;
				CardNode temp2=curr.next.next.next;
				prev.next=curr.next;
				prev.next.next.next=temp1;
				temp1.next=temp2;
				return;
			}
		}
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		if((deckRear.cardValue==27||deckRear.cardValue==28)&&(deckRear.next.cardValue==27||deckRear.next.cardValue==28)){
			return;
		}
		else if(deckRear.cardValue==27||deckRear.cardValue==28){
			CardNode a=deckRear.next;
			for(;a!=deckRear;a=a.next){
				if(a.next.cardValue==27||a.next.cardValue==28){
				deckRear=a;
				return;
				}
			}
		}
		else if(deckRear.next.cardValue==27||deckRear.next.cardValue==28){
			CardNode a=deckRear.next.next;
			for(;a!=deckRear;a=a.next){
				if(a.cardValue==27||a.cardValue==28){
					deckRear=a;
					return;
					}
				}
			}
		else{
			CardNode head=deckRear.next;
			CardNode a=head;
			for(;a!=deckRear;a=a.next){
				if(a.next.cardValue==27||a.next.cardValue==28){
					break;
				}
			}
			CardNode b=a.next;
			CardNode c=b.next;
			for(;c!=deckRear;c=c.next){
				if(c.cardValue==27||c.cardValue==28){
					break;
				}
			}
			CardNode d=c.next;
			deckRear.next=b;
			c.next=head;
			a.next=d;
			deckRear=a;
			return;
		}
	}
	
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {
		CardNode head=deckRear.next;
		CardNode curr=head;
		int lastnum,n=1;
		if(deckRear.cardValue==28)
			lastnum = 27;
		else
			lastnum = deckRear.cardValue;
		
		for(;n<lastnum;n++)
		{
			curr=curr.next;
		}
		CardNode a=curr.next;
		CardNode b=a;
		if(curr.next==deckRear)
			return;
			for(;b!=deckRear;b=b.next){
				if(b.next==deckRear)
					break;
			}
			curr.next=deckRear;
			b.next=head;
			deckRear.next=a;
	}
	
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		int key=-1,n=1;
		int headvalue=deckRear.next.cardValue;
		if(headvalue==28)
			headvalue=27;
		CardNode curr=deckRear.next;
		for(;n<=headvalue;n++)
		{
			if (n==headvalue)
			{
				if(curr.next.cardValue == 27 || curr.next.cardValue == 28)
				{
					jokerA();
					jokerB();
					tripleCut();
					countCut();
					curr=deckRear;
					n=0;
					headvalue=deckRear.next.cardValue;
					if(headvalue==28)
						headvalue=27;
				}
				else
				{
					key = curr.next.cardValue;
					return key;
				}
			}
			curr = curr.next;
		}
		return key;
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		String sentence="";
		for(int i=0;i<message.length();i++){
			if(Character.isLetter(message.charAt(i))){
				jokerA();
				jokerB();
				tripleCut();
				countCut();
				char ch=Character.toUpperCase(message.charAt(i));
				int a=ch-'A'+1;
				int key=getKey();
				int code=a+key;
				if(code>26)
					code-=26;
				ch=(char)(code-1+'A');
				sentence+=ch;
			}
				
			else
				continue;
		}
	    return sentence;

	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		String sentence="";
		for(int i=0 ;i<message.length();i++){
			jokerA();
			jokerB();
			tripleCut();
			countCut();
			char ch=Character.toUpperCase(message.charAt(i));
			int a=ch-'A'+1;
			int key=getKey();
			int code=a-key;
			if (code<=0)
				code+=26;
			ch=(char)(code-1+'A');
			sentence+=ch;
		}
		return sentence;
	}
}
