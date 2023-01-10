package tech.wetech.admin3.common;


import java.util.Collection;
import java.util.List;

/**
 * @author cjbi
 * @date 2022/7/22
 */
public interface EventStore {

    void append(DomainEvent aDomainEvent);

}
