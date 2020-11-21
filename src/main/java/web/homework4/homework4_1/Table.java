package web.homework4.homework4_1;

import lombok.Data;

import java.util.Vector;

@Data
public class Table {
    Vector<Contact> table;
    Table()
    {
        table = new Vector<Contact>();
        table.add(new Contact("张三","18075398612","18075398612@163.com","青柠街1号","18075398612","小张"));
        table.add(new Contact("李四","18075398613","18075398613@163.com","青柠街2号","18075398613","小李"));
        table.add(new Contact("王五","18075398614","18075398614@163.com","青柠街3号","18075398614","小王"));
    }
}
