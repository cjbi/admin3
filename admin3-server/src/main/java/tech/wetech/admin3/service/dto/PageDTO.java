package tech.wetech.admin3.service.dto;

import java.util.List;

/**
 * @author cjbi
 */
public record PageDTO<T>(List<T> list, long total) {

}
