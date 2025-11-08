package application;

public class StackAndQueueNode {
	private Object element;
	private StackAndQueueNode next;

	StackAndQueueNode(Object element) {
		this(element, null);
	}

	StackAndQueueNode(Object element, StackAndQueueNode next) {
		this.element = element;
		this.next = next;
	}

	public Object getElement() {
		return element;
	}

	public void setElement(Object element) {
		this.element = element;
	}

	public StackAndQueueNode getNext() {
		return next;
	}

	public void setNext(StackAndQueueNode next) {
		this.next = next;
	}

}
