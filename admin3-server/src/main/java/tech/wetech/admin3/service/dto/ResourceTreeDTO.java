package tech.wetech.admin3.service.dto;

import tech.wetech.admin3.model.Resource.Type;

import java.util.List;

/**
 * @author cjbi
 */
public record ResourceTreeDTO(Long id, String name, Type type, String permission, String url,
                              List<ResourceTreeDTO> children) {

}
