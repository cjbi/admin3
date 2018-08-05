package tech.wetech.admin.modules.system.dto;

/**
 *
 * @author cjbi
 */
public class TreeDto {

    private Long id;
    private Long pId;
    private String name;
    private boolean parent;
    private Object obj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public TreeDto() {

    }

    public Long getPId() {
        return pId;
    }

    public void setPId(Long pId) {
        this.pId = pId;
    }

    public boolean getIsParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public TreeDto(Long id, Long pId, String name, boolean parent, Object obj) {
        super();
        this.id = id;
        this.pId = pId;
        this.name = name;
        this.parent = parent;
        this.obj = obj;
    }

}
