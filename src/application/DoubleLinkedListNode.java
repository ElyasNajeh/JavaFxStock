package application;

public class DoubleLinkedListNode {
	private Object element;
	private DoubleLinkedListNode next, prev;

	DoubleLinkedListNode(Object element) {
		this(element, null, null);
	}

	DoubleLinkedListNode(Object element, DoubleLinkedListNode next, DoubleLinkedListNode prev) {
		this.element = element;
		this.next = next;
		this.prev = prev;
	}

	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}

	public DoubleLinkedListNode getNext() {
		return next;
	}

	public void setNext(DoubleLinkedListNode next) {
		this.next = next;
	}

	public DoubleLinkedListNode getPrev() {
		return prev;
	}

	public void setPrev(DoubleLinkedListNode prev) {
		this.prev = prev;
	}

}
