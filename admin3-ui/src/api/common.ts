import {BASE_URI} from './base';
import request from "../utils/request";

export function getEventTypes() {
  return request({
    url: `${BASE_URI}/common/event-types`
  });
};
