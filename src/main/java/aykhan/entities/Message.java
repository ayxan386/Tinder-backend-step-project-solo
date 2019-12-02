package aykhan.entities;

public class Message {
  private final String content;
  private final int sender;
  private final int receiver;

  public Message(String content, int sender, int receiver) {
    this.content = content;
    this.sender = sender;
    this.receiver = receiver;
  }

  public String getContent() {
    return content;
  }

  public int getSender() {
    return sender;
  }

  public int getReceiver() {
    return receiver;
  }

  @Override
  public String toString() {
    return String.format("Message{content='%s', sender=%d, receiver=%d}", content, sender, receiver);
  }
}
