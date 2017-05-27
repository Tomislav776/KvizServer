package hr.project.exceptionHandling;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObjectNotFound  extends RuntimeException{

    private Integer objectId;

    public Integer getObjectId() {
        return objectId;
    }
}
