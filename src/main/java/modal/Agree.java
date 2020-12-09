package modal;

import lombok.Data;

@Data
public class Agree {
    private Integer id;
    private Integer message_id;
    private Integer agreeperson_id;
    public Agree(Integer id,Integer message_id,Integer agreeperson_id)
    {
        this.id = id;
        this.message_id = message_id;
        this.agreeperson_id = agreeperson_id;
    }
}
