package modal;

import lombok.Data;

@Data
public class Comments {
    private Integer id;
    private String name;
    private String comment;
    private Integer relate;

    public Comments(Integer id, String name,String comment,Integer relate)
    {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.relate = relate;
    }


}
