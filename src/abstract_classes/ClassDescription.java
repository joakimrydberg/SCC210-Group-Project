package abstract_classes;

import java.io.Serializable;

/**
 * @author josh
 * @date 23/02/16.
 */
public abstract class ClassDescription implements Serializable {
    private static final long serialVersionUID = 55L;  //actually needed
    public String name = null;
    public Class type = null;

    public ClassDescription(String name) {
        this.name = name;
        this.type = this.getClass();
    }

    /**
     * Sets the name, (which will be returned be toString)
     *
     * @param name - String name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the name
     *
     * @return - String name
     */
    public String getName() {
        return name;
    }

}
