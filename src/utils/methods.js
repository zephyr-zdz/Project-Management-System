/**
 * Helper for return name according to number
 * @param list eg. allTeacher
 * @param id eg. "22222222"
 * @param value eg. "number"
 * @param label eg. "name"
 * @returns {*}
 */
export function getLabel(list, id, value, label) {
  if (id !== '' && Array.isArray(list) && list.length !== 0){
    return !list.find(item => item[value] === id) ? id : list.find(item => item[value] === id)[label]
  } else {
    return id
  }
}

