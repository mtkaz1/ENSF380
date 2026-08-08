
package part2.edu.ucalgary.oop;

class Animal {
	private int age;

	public Animal(int age) {
		this.setAge(age);
	}

	public Animal() {
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void eat() {
		System.out.println("Nom nom nom");
	}
}

class Reptile extends Animal {
	private String scales;

	public Reptile(int age) {
		super(age);
	}

	public Reptile(int age, String scales) {
		super(age);
		setScales(scales);
	}

	
	public String getScales() {
		return this.scales;
	}

	public void setScales(String scales) {
		this.scales = scales;
	}
}


class Lizard extends Reptile {
	private int tailLength;
	private int tongueLength;

	public Lizard(int age) {
		super(age);
	}

	
	public Lizard(String scales, int age) {
		super(age, scales);
	}

	public Lizard(String scales, int age, int tail, int tongue) {
		super(age, scales);
		this.setTailLength(tail);
		this.setTongueLength(tongue);
	}

	public void setTailLength(int tail) {
		this.tailLength = tail;
	}

	public int getTailLength() {
		return this.tailLength;
	}

	public void setTongueLength(int tongue) {
		this.tongueLength = tongue;
	}

	public int getTongueLength() {
		return this.tongueLength;
	}

	public void run() {
		System.out.println("ITS RUNNING!");
	}
}

public class MyExample {

	public static void  main(String[] args) {
		Lizard komodoDragon = new Lizard("black", 5, 55, 15);
		Lizard gecko = new Lizard("green", 1, 3, 1);

		System.out.println("Komodo Dragons are " +
			komodoDragon.getScales() + " and " +
			"this one is " + komodoDragon.getAge() +
			" years old, with a tail " + komodoDragon.getTailLength() +
			" centimeters long and a tongue of " +
			komodoDragon.getTongueLength() +
			" centimeter. Watch it run!");
		komodoDragon.run();
		komodoDragon.eat();

		System.out.println("This gecko is " +
			gecko.getScales() + " and is " +
			gecko.getAge() + " years old, with a tail "
			+ gecko.getTailLength() + " centimeters long,"+
			" and a tongue of " +
			gecko.getTongueLength() + " centimeter. Watch it run!");
		gecko.run();
		gecko.eat();
	}
}
