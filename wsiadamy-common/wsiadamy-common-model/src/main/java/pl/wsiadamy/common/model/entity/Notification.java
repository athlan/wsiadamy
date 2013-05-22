package pl.wsiadamy.common.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import pl.wsiadamy.common.model.common.AbstractEntity;

@SuppressWarnings("serial")
@Document
public class Notification extends AbstractEntity<String> {
 
    @Id
    private String id;
    private String name;
     
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
