package mitso.volodymyr.tryretrofit.models;

// @Generated("org.jsonschema2pojo")

public class Company {

    private String name;
    private String catchPhrase;
    private String bs;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The catchPhrase
     */
    public String getCatchPhrase() {
        return catchPhrase;
    }

    /**
     *
     * @param catchPhrase
     * The catchPhrase
     */
    public void setCatchPhrase(String catchPhrase) {
        this.catchPhrase = catchPhrase;
    }

    /**
     *
     * @return
     * The bs
     */
    public String getBs() {
        return bs;
    }

    /**
     *
     * @param bs
     * The bs
     */
    public void setBs(String bs) {
        this.bs = bs;
    }

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' +
                ", catchPhrase='" + catchPhrase + '\'' +
                ", bs='" + bs + '\'' +
                '}';
    }
}

