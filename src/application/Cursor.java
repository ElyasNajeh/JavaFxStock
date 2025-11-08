package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Cursor {
	CursorNode[] cursorArray;
	private static int MAX_SIZE;

	public Cursor(int MAX_SIZE) {
		this.MAX_SIZE = MAX_SIZE;
		cursorArray = new CursorNode[MAX_SIZE];
		initialization();
	}

	public void initialization() {
		cursorArray = new CursorNode[MAX_SIZE];
		for (int i = 0; i < MAX_SIZE; i++) {
			cursorArray[i] = new CursorNode(null, i + 1);
		}
		cursorArray[MAX_SIZE - 1].setNext(0);
	}

	public int cursorAlloc() {
		int p = cursorArray[0].getNext();
		cursorArray[0].setNext(cursorArray[p].getNext());
		return p;
	}

	public int createList() {
		int l = cursorAlloc();
		if (l == 0) {
			System.out.println("No Free Space");
		}
		cursorArray[l] = new CursorNode("-", 0);
		return l;
	}

	public void cursorFree(int p) {
		cursorArray[p].setNext(cursorArray[0].getNext());
		cursorArray[0].setNext(p);
	}

	public boolean isEmpty(int p) {
		return cursorArray[p].getNext() == 0;
	}

	public boolean isLast(int l) {
		return cursorArray[l].getNext() == 0;
	}

	public void insertAtBack(Object element, int l) {
		int p = cursorAlloc();
		if (p != 0) {
			cursorArray[p] = new CursorNode(element, 0);
			if (isEmpty(l)) {
				cursorArray[l].setNext(p);
				return;
			}
			int current = cursorArray[l].getNext();
			while (cursorArray[current].getNext() != 0) {
				current = cursorArray[current].getNext();
			}
			cursorArray[current].setNext(p);

		} else {
			System.out.println("No Free Space");
		}
	}

	public int findPrev(Object element, int l) {
		int prev = l;
		int current = cursorArray[l].getNext();
		while (current != 0 && !cursorArray[current].getElement().equals(element)) {
			prev = current;
			current = cursorArray[current].getNext();
		}
		return prev;
	}

	public boolean remove(Object element, int l) {
		if (isEmpty(l)) {
			return false;
		} else {
			int pos = findPrev(element, l);
			if (cursorArray[pos].getNext() != 0) {
				int temp = cursorArray[pos].getNext();
				cursorArray[pos].setNext(cursorArray[temp].getNext());
				cursorFree(temp);
			}
		}
		return true;
	}

	// Searches for a product by ID or name
	public ObservableList<Product> searchProduct(String search, int l) {
		ObservableList<Product> resultofSearch = FXCollections.observableArrayList();
		int current = cursorArray[l].getNext();
		Alerts alerts = new Alerts();
		if (search == null || search.trim().isEmpty()) {
			alerts.ErrorAlert("Error", "Please Enter Product ID or Name to Search");
			return resultofSearch;
		}

		// Determine if search input is numeric
		boolean Num = true;
		for (int i = 0; i < search.length(); i++) {
			if (!Character.isDigit(search.charAt(i))) {
				Num = false;
				break;
			}
		}
		while (current != 0) {
			Product product = (Product) cursorArray[current].getElement();
			if (Num && product.getProductId() == Integer.parseInt(search)) {
				resultofSearch.add(product);
			} else if (product.getProductName().equalsIgnoreCase(search)) {
				resultofSearch.add(product);
			}
			current = cursorArray[current].getNext();
		}
		if (resultofSearch.isEmpty()) {
			alerts.ErrorAlert("Error", "No Product Found with this Name");
		}
		return resultofSearch;

	}

	// Sorts the product list by name
	public void sortProductsByName(int l) {
		int i = cursorArray[l].getNext();
		while (i != 0) {
			int j = cursorArray[i].getNext();
			while (j != 0) {
				Product pi = (Product) cursorArray[i].getElement();
				Product pj = (Product) cursorArray[j].getElement();
				if (pi.getProductName().compareToIgnoreCase(pj.getProductName()) > 0) {
					cursorArray[i].setElement(pj);
					cursorArray[j].setElement(pi);
				}
				j = cursorArray[j].getNext();
			}
			i = cursorArray[i].getNext();
		}
	}
	// Sorts the product list by category name
	public void sortProductByCategory(int l) {
		int i = cursorArray[l].getNext();
		while (i != 0) {
			int j = cursorArray[i].getNext();
			while (j != 0) {
				Product pi = (Product) cursorArray[i].getElement();
				Product pj = (Product) cursorArray[j].getElement();
				if (pi.getCategory().getCategoryName().compareToIgnoreCase(pj.getCategory().getCategoryName()) > 0) {
					cursorArray[i].setElement(pj);
					cursorArray[j].setElement(pi);
				}
				j = cursorArray[j].getNext();
			}
			i = cursorArray[i].getNext();
		}
	}
	// Sorts products by status
	public void sortProductByStatus(int l) {
		int j = l;
		int i = cursorArray[l].getNext();
		while (i != 0) {
			Product pi = (Product) cursorArray[i].getElement();
			if (pi.getStatus().equals("Active")) {
				j = cursorArray[j].getNext();
				if (j != i) {
					Product pj = (Product) cursorArray[j].getElement();
					cursorArray[j].setElement(pi);
					cursorArray[i].setElement(pj);
				}
			}
			i = cursorArray[i].getNext();
		}
	}

	// Returns all products in the list 
	public ObservableList<Product> List(int l) {
		ObservableList<Product> list = FXCollections.observableArrayList();
		int current = cursorArray[l].getNext();
		while (current != 0) {
			list.add((Product) cursorArray[current].getElement());
			current = cursorArray[current].getNext();
		}
		return list;
	}

	public int size(int l) {
		int count = 0;
		int current = cursorArray[l].getNext();
		while (current != 0) {
			count++;
			current = cursorArray[current].getNext();
		}
		return count;
	}

	public void printList(int l) {
		if (isEmpty(l)) {
			return;
		}
		int current = cursorArray[l].getNext();
		while (current != 0) {
			System.out.print(cursorArray[current].getElement() + " --> ");
			current = cursorArray[current].getNext();
		}
	}
}
