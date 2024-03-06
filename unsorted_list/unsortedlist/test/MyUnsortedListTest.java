import datastruct.*;
import org.junit.*;

import java.util.Random;
import java.util.Vector;

import static org.junit.Assert.*;


public class MyUnsortedListTest {

	protected UnsortedList<Integer> unsortedIntList;
	protected UnsortedList<String> unsortedStringList;
	protected Random random = new Random();


	//On refait une nouvelle liste à chaque nouveau test
	@Before
	public void initListTest() {
		unsortedIntList = MyUnsortedList.of();
		assertEquals(0, unsortedIntList.size());
		assertTrue(unsortedIntList.isEmpty());

		unsortedStringList = MyUnsortedList.of();
		assertEquals(0, unsortedStringList.size());
		assertTrue(unsortedStringList.isEmpty());
	}

	//On essaie la création de l'UnsortedList à partir d'un Iterable
	@Test
	public void initListIteratorTest(){
		Vector<Integer> vector = new Vector<>();
		vector.add(1);
		vector.add(2);
		vector.add(3);

		UnsortedList<Integer> unsortedIntCandidate = MyUnsortedList.of(vector);
		assertEquals(Integer.valueOf(1), unsortedIntCandidate.pop());
		assertEquals(Integer.valueOf(2), unsortedIntCandidate.pop());
		assertEquals(Integer.valueOf(3), unsortedIntCandidate.pop());
	}


	@Test
	//Simple append en vérifiant la taille, on teste aussi les négatifs.
	public void appendIntTest() {
		int firstInt = random.nextInt(30 + 30) - 30;
		int secondInt = random.nextInt(30 + 30) - 30;
		int thirdInt = random.nextInt(30 + 30) - 30;

		unsortedIntList.append(firstInt);
		assertEquals(1, unsortedIntList.size());
		assertFalse(unsortedIntList.isEmpty());

		unsortedIntList.append(secondInt);
		assertEquals(2, unsortedIntList.size());
		assertFalse(unsortedIntList.isEmpty());

		unsortedIntList.append(thirdInt);
		assertEquals(3, unsortedIntList.size());
		assertFalse(unsortedIntList.isEmpty());
	}

	@Test
	//Simple append avec des Strings
	//Il ne semble pas nécéssaire de vérifier avec plusieurs types?? On utilise le type générique E dans l'implémentation de MyUnsortedList
	public void appendStringTest() {
		String firstString = "J'adore";
		String secondString = "JUnit même si la version";
		String thirdString = "5 est mieux grâce à @RepeatableTest()";

		unsortedStringList.append(firstString);
		assertEquals(1, unsortedStringList.size());
		assertFalse(unsortedStringList.isEmpty());

		unsortedStringList.append(secondString);
		assertEquals(2, unsortedStringList.size());
		assertFalse(unsortedStringList.isEmpty());

		unsortedStringList.append(thirdString);
		assertEquals(3, unsortedStringList.size());
		assertFalse(unsortedStringList.isEmpty());
	}

	//Test de prepend(), avec pop(), tout en vérifiant la taille
	@Test
	public void prependInt() {
		unsortedIntList.prepend(1);
		unsortedIntList.prepend(2);
		unsortedIntList.prepend(3);

		assertEquals(3, unsortedIntList.size());
		assertFalse(unsortedIntList.isEmpty());

		assertEquals(Integer.valueOf(3), unsortedIntList.pop());
		assertEquals(2, unsortedIntList.size());
		assertEquals(Integer.valueOf(2), unsortedIntList.pop());
		assertEquals(1, unsortedIntList.size());
		assertEquals(Integer.valueOf(1), unsortedIntList.pop());
		assertEquals(0, unsortedIntList.size());
	}

	//Test de preprend() avec des strings
	@Test
	public void prependString() {
		unsortedStringList.prepend("Fonctionner");
		unsortedStringList.prepend("Devrais");
		unsortedStringList.prepend("Tu");

		assertEquals(3, unsortedStringList.size());
		assertFalse(unsortedStringList.isEmpty());

		assertEquals("Tu", unsortedStringList.pop());
		assertEquals(2, unsortedStringList.size());
		assertEquals("Devrais", unsortedStringList.pop());
		assertEquals(1, unsortedStringList.size());
		assertEquals("Fonctionner", unsortedStringList.pop());
		assertEquals(0, unsortedStringList.size());
	}

	//Test de insert(), en insérant à la position 0, avec pop() et en vérifiant la size
	@Test
	public void insertAtZeroTest() {
		//Insérer à 0
		unsortedIntList.insert(1, 0);
		assertEquals(1, unsortedIntList.size());
		unsortedIntList.insert(2, 0);
		assertEquals(2, unsortedIntList.size());
		unsortedIntList.insert(3, 0);
		assertEquals(3, unsortedIntList.size());

		assertEquals(Integer.valueOf(3), unsortedIntList.pop());
		assertEquals(2, unsortedIntList.size());
		assertEquals(Integer.valueOf(2), unsortedIntList.pop());
		assertEquals(1, unsortedIntList.size());
		assertEquals(Integer.valueOf(1), unsortedIntList.pop());
		assertEquals(0, unsortedIntList.size());
	}

	//Test de insert(), en insérant à la position .size(), avec pop() et en vérifiant la size
	@Test
	public void insertAtSizeTest(){
		unsortedIntList.insert(1, unsortedIntList.size());
		assertEquals(1, unsortedIntList.size());
		unsortedIntList.insert(2, unsortedIntList.size());
		assertEquals(2, unsortedIntList.size());
		unsortedIntList.insert(3, unsortedIntList.size());
		assertEquals(3, unsortedIntList.size());

		assertEquals(Integer.valueOf(1), unsortedIntList.pop());
		assertEquals(2, unsortedIntList.size());
		assertEquals(Integer.valueOf(2), unsortedIntList.pop());
		assertEquals(1, unsortedIntList.size());
		assertEquals(Integer.valueOf(3), unsortedIntList.pop());
		assertEquals(0, unsortedIntList.size());
	}


	//Test le renvoi de l'exception de insert, en insérant à MAX_VALUE
	//Peut renvoyer un faux positif si la liste a MAX_VALUE élements (très peu probable)
	@Test(expected = IndexOutOfBoundsException.class)
	public void insertExceptionTest_First() {
		unsortedIntList.insert(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	//Test le renvoi de l'exception de insert, en insérant à MIN_VALUE
	@Test(expected = IndexOutOfBoundsException.class)
	public void insertExceptionTest_Second() {
		unsortedIntList.insert(Integer.MIN_VALUE, Integer.MIN_VALUE);
	}

	//Test le renvoi de l'exception de insert, en insérant à -1
	@Test(expected = IndexOutOfBoundsException.class)
	public void insertExceptionTest_Third() {
		unsortedIntList.insert(Integer.MIN_VALUE, -1);
	}

	//On considère que pop() est testé, il est utilisé dans tous les autres tests. Il faut juste tester les exceptions.

	//Test le renvoi de l'exception de pop, en ajoutant d'abord un élément et en pop() deux fois.
	@Test(expected = EmptyListException.class)
	public void popExceptionTest_First() {
		unsortedIntList.append(1);
		unsortedIntList.pop();
		assertEquals(0, unsortedIntList.size());

		unsortedIntList.pop();
		assertEquals(0, unsortedIntList.size());

		unsortedIntList.pop();
		assertEquals(0, unsortedIntList.size()); //On vérifie quand même que la taille n'ait pas changé
	}

	//Test le renvoi de l'exception de pop, en utilisant pop() immédiatement
	@Test(expected = EmptyListException.class)
	public void popExceptionTest_Second() {
		unsortedIntList.pop();
		assertEquals(0, unsortedIntList.size());
	}


	//Test de popLast(), on vérifie aussi les tailles.
	/**
	 * FOUNDBUG: Le second popLast ne fait pas diminuer la taille de la liste, plus d'informations dans {@link #removeTest()}
	 */
	@Test
	public void popLast() {
		unsortedIntList.append(1);
		unsortedIntList.append(2);
		unsortedIntList.append(3);
		unsortedIntList.append(4);
		unsortedIntList.append(5);

		assertEquals(Integer.valueOf(5), unsortedIntList.popLast());
		assertEquals(4, unsortedIntList.size());
		assertEquals(Integer.valueOf(4), unsortedIntList.popLast());
		assertEquals(3, unsortedIntList.size());
		assertEquals(Integer.valueOf(3), unsortedIntList.popLast());
		assertEquals(2, unsortedIntList.size());
		assertEquals(Integer.valueOf(2), unsortedIntList.popLast());
		assertEquals(1, unsortedIntList.size());
		assertEquals(Integer.valueOf(1), unsortedIntList.popLast());
		assertEquals(0, unsortedIntList.size());
	}


	//Test des exceptions de popLast(), on append() puis on pop() deux fois.
	@Test(expected = EmptyListException.class)
	public void popLastExceptionTest_First() {
		unsortedIntList.append(1);
		assertEquals(1, unsortedIntList.size());

		unsortedIntList.popLast();
		assertEquals(0, unsortedIntList.size());
		unsortedIntList.popLast();
		assertEquals(0, unsortedIntList.size());
	}


	//Test des exceptions de popLast(), on pop() immédiatement.
	@Test(expected = EmptyListException.class)
	public void popLastExceptionTest_Second() {
		unsortedIntList.popLast();
		assertEquals(0, unsortedIntList.size());
	}


	//Test de remove(), on vérifie aussi les tailles.
	@Test
	//FOUNDBUG: remove ne diminue pas la taille de la liste si on enlève autre que le dernier élément
	public void removeTest() {
		unsortedIntList.append(1);
		unsortedIntList.append(2);
		unsortedIntList.append(3);

		assertEquals(Integer.valueOf(3), unsortedIntList.remove(2));
		assertEquals(2, unsortedIntList.size());
		assertEquals(Integer.valueOf(2), unsortedIntList.remove(1));
		assertEquals(1, unsortedIntList.size());
		assertEquals(Integer.valueOf(1), unsortedIntList.remove(0));
		assertEquals(0, unsortedIntList.size());
	}


	//Test les exceptions de remove(), on essaie d'enlever à la position MIN_VALUE
	@Test(expected = IndexOutOfBoundsException.class)
	public void removeExceptionTest_First() {
		unsortedIntList.remove(Integer.MIN_VALUE);
		assertEquals(0, unsortedIntList.size());
	}


	//Test les exceptions de remove(), on essaie d'enlever à la position 0
	@Test(expected = IndexOutOfBoundsException.class)
	public void removeExceptionTest_Second() {
		unsortedIntList.remove(0);
		assertEquals(0, unsortedIntList.size());
	}

	//Test les exceptions de remove(), on essaie d'enlever à la position -1
	@Test(expected = IndexOutOfBoundsException.class)
	public void removeExceptionTest_Third() {
		unsortedIntList.remove(-1);
		assertEquals(0, unsortedIntList.size());
	}
	//Test les exceptions de remove(), on essaie d'enlever à la position 1 dans une liste à 1 élément.
	@Test(expected = IndexOutOfBoundsException.class)
	public void removeExceptionTest_Fourth() {
		unsortedIntList.append(1);
		unsortedIntList.remove(1);
		assertEquals(0, unsortedIntList.size());
	}


	//Test de equal, on test aussi le mirroir (list1 ?= list2) ET (list2 ?= list1)
	//On est aussi les listes vidées et avec un autre Objet
	@Test
	public void testEquals() {
		UnsortedList<Integer> unsortedListCandidate = MyUnsortedList.of();
		unsortedListCandidate.append(1);
		unsortedListCandidate.append(2);
		unsortedListCandidate.append(3);

		unsortedIntList.append(1);
		unsortedIntList.append(2);
		unsortedIntList.append(3);

		assertEquals(unsortedIntList, unsortedListCandidate);
		assertEquals(unsortedListCandidate, unsortedIntList);

		unsortedIntList.pop();
		unsortedIntList.pop();
		unsortedIntList.pop();

		unsortedListCandidate.pop();
		unsortedListCandidate.pop();
		unsortedListCandidate.pop();

		assertEquals(unsortedIntList, unsortedListCandidate);
		assertNotEquals(unsortedIntList, 1);
	}


	//Test de equal, on teste la différence
	@Test
	public void testNotEquals(){
		UnsortedList<Integer> unsortedListCandidate = MyUnsortedList.of();
		unsortedListCandidate.append(1);
		unsortedListCandidate.append(2);
		unsortedListCandidate.append(3);
		unsortedListCandidate.append(4);

		unsortedIntList.append(1);
		unsortedIntList.append(2);
		unsortedIntList.append(3);

		assertNotEquals(unsortedIntList, unsortedListCandidate);
		assertNotEquals(unsortedListCandidate, unsortedIntList);

		unsortedIntList.append(5);
		assertNotEquals(unsortedIntList, unsortedListCandidate);
		assertNotEquals(unsortedListCandidate, unsortedIntList);
	}

	//On teste le toString, un peu primitif il faudrait tester avec des strings en utilisant .contains()
	@Test
	public void testToString() {
		unsortedIntList.append(1);

		String expectedToString = "MyUnsortedList { size = 1, [1] }";

		assertEquals(expectedToString, unsortedIntList.toString());

		unsortedIntList.append(2);
		expectedToString = "MyUnsortedList { size = 2, [1, 2] }";

		assertEquals(expectedToString, unsortedIntList.toString());
	}
}