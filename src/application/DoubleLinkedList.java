package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DoubleLinkedList {
	private DoubleLinkedListNode Front, Back;
	private int Size;

	DoubleLinkedList() {
		this.Front = null;
		this.Back = null;
		Size = 0;
	}

	public DoubleLinkedListNode getFront() {
		return Front;
	}

	public void setFront(DoubleLinkedListNode front) {
		Front = front;
	}

	public DoubleLinkedListNode getBack() {
		return Back;
	}

	public void setBack(DoubleLinkedListNode back) {
		Back = back;
	}

	public int getSize() {
		return Size;
	}

	public void setSize(int size) {
		Size = size;
	}

	public Object getFirst() {
		if (getSize() == 0) {
			return null;
		} else {
			return getFront().getElement();
		}
	}

	public Object getLast() {
		if (getSize() == 0) {
			return null;
		} else {
			return getBack().getElement();
		}
	}

	public void addLast(Object element) {
		DoubleLinkedListNode newNode = new DoubleLinkedListNode(element);
		if (getSize() == 0) {
			setFront(newNode);
			setBack(newNode);
		} else {
			getBack().setNext(newNode);
			newNode.setPrev(getBack());
			setBack(newNode);

		}
		Size++;
	}

	public Object get(int index) {
		if (getSize() == 0) {
			return null;
		} else if (index == 0) {
			return getFirst();
		} else if (index == getSize() - 1) {
			return getLast();
		} else if (index > 0 && index < getSize() - 1) {
			DoubleLinkedListNode current = getFront();
			for (int i = 0; i < index; i++) {
				current = current.getNext();
			}
			return current.getElement();
		} else {
			return null;
		}
	}

	public boolean removeFirst() {
		if (getSize() == 0) {
			return false;
		} else if (getSize() == 1) {
			setFront(null);
			setBack(null);

		} else {
			setFront(getFront().getNext());
			getFront().setPrev(null);
		}
		Size--;
		return true;
	}

	public boolean removeLast() {
		if (getSize() == 0) {
			return false;
		} else if (getSize() == 1) {
			setFront(null);
			setBack(null);
		} else {
			setBack(getBack().getPrev());
			getBack().setNext(null);
		}
		Size--;
		return true;
	}

	public boolean remove(int index) {
		if (getSize() == 0) {
			return false;
		} else if (index == 0) {
			removeFirst();
		} else if (index >= getSize() - 1) {
			removeLast();
		} else if (index > 0 && index < getSize() - 1) {
			DoubleLinkedListNode current = getFront();
			for (int i = 0; i < index; i++) {
				current = current.getNext();
			}
			current.getPrev().setNext(current.getNext());
			current.getNext().setPrev(current.getPrev());
			current = null;
			Size--;
		}
		return true;

	}

	// Searches for a category by its name
	public ObservableList<Category> searchCategorybyName(String search) {
		ObservableList<Category> resultofSearch = FXCollections.observableArrayList();
		DoubleLinkedListNode current = getFront();
		Alerts alerts = new Alerts();
		if (search == null || search.trim().isEmpty()) {
			alerts.ErrorAlert("Error", "Please Enter Category Name to Search");
			return resultofSearch;
		}
		while (current != null) {
			Category category = (Category) current.getElement();
			if (category.getCategoryName().equalsIgnoreCase(search)) {
				resultofSearch.add(category);
			}
			current = current.getNext();
		}
		if (resultofSearch.isEmpty()) {
			alerts.ErrorAlert("Error", "No Category Found with this Name");
		}
		return resultofSearch;

	}

	public void clear() {
		setFront(null);
		setBack(null);
		Size = 0;
	}

	public void printList() {
		DoubleLinkedListNode current = getFront();
		while (current != null) {
			System.out.print(current.getElement() + " < -- > ");
			current = current.getNext();
		}
		System.out.println("null");
	}
}
