package model;

public class Archive {
    private String command;
    private String page;
    private String size;

    public Archive(String command, String page, String size) {
        this.command = command;
        this.page = page;
        this.size = size;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
