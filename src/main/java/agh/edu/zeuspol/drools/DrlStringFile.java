package agh.edu.zeuspol.drools;

public class DrlStringFile {
    private String path;
    private String fileContent;
    public DrlStringFile(String path, String fileContent) {
        this.path = path;
        this.fileContent = fileContent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
