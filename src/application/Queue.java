package application;

public class Queue {
	private StackAndQueueNode Front;
	private StackAndQueueNode rear;
	private int Size;

	Queue() {
		this.Front = null;
		this.rear = null;
		this.Size = 0;
	}

	public StackAndQueueNode getFront() {
		return Front;
	}

	public void setFront(StackAndQueueNode front) {
		Front = front;
	}

	public StackAndQueueNode getRear() {
		return rear;
	}

	public void setRear(StackAndQueueNode rear) {
		this.rear = rear;
	}

	public int getSize() {
		return Size;
	}

	public void setSize(int size) {
		Size = size;
	}
	public boolean isEmpty() {
		return getFront() == null;
	}

	public void enQueue(Object element) {
		StackAndQueueNode newNode = new StackAndQueueNode(element);

		if (getFront() == null) {
			setFront(newNode);
			setRear(newNode);
		} else {
			getRear().setNext(newNode);
			setRear(newNode);
			Size++;
		}
	}

	public Object deQueue() {
		if (isEmpty()) {
			System.out.println("Queue is Empty");
			return null;
		} else {
			StackAndQueueNode temp = getFront();
			setFront(getFront().getNext());
			Size--;
			return temp.getElement();

		}
	}

	public void clear() {
		setFront(null);
		setRear(null);
		Size = 0;
	}

	public Object peek() {
		if (isEmpty()) {
			return null;
		} else {
			return getFront().getElement();
		}
	}

	public void printList() {
		Queue q2 = new Queue();
		while (!isEmpty()) {
			System.out.println(peek());
			q2.enQueue(deQueue());
		}
		while (!q2.isEmpty()) {
			enQueue(q2.deQueue());
		}
	}
}
