package game;

/**
 * @author Alexander J Mills
 * @date 15/02/16.
 */
public class LevelPart {
    private String spriteFileName = "";
    private float rotation = 0;
    private short rowNo = 0, colNo = 0; // position on grid just in case

    public LevelPart(String fileName, float rotation, short rowNo, short colNo) {
        if (rowNo < 0 || rowNo >= 11
                && colNo < 0 || colNo >= 11)
            throw new IllegalArgumentException("Invalid rowNo / colNo. Must be between 0 and 10 inc.");

        this.spriteFileName = fileName;
        this.rotation = rotation;
        this.rowNo = rowNo;
        this.colNo = colNo;
    }

    public void setRowNo(int rowNo) {
        if (rowNo < 0 || rowNo >= 11
                && colNo < 0 || colNo >= 11)
            throw new IllegalArgumentException("Invalid row No. Must be between 0 and 10 inc.");
        else
            this.rowNo = (short) rowNo;
    }

    public void setColNo(int colNo) {
        if (rowNo < 0 || rowNo >= 11
                && colNo < 0 || colNo >= 11)
            throw new IllegalArgumentException("Invalid col No. Must be between 0 and 10 inc.");
        else
            this.colNo = (short) colNo;
    }

    public void setSpriteFileName(String spriteFileName) {
        this.spriteFileName = spriteFileName;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public String getSpriteFileName() {
        return spriteFileName;
    }

    public float getRotation() {
        return rotation;
    }

    public int getRowNo() {
        return rowNo;
    }

    public int getColNo() {
        return colNo;
    }
}
