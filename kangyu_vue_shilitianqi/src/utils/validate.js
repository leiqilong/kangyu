/*
 *验证电话号码
 */
const validatePhone = function (rule, value, callback) {
  if (/^1[345678]\d{9}$/.test(value) == false) {
    callback(new Error("请输入正确的电话"));
  } else {
    callback();
  }
}

/*
 *验证输入是否数字
 */
const validateNumber = function (rule, value, callback) {
  if (/^\d+$/.test(value) == false) {
    callback(new Error("请输入数字值！"));
    return;
  }
  let minValue = parseInt(rule.minValue) || 0;
  let maxValue = parseInt(rule.maxValue) || 0;
  if (minValue === maxValue && minValue === 0) {
    callback();
    return;
  }

  if (value < minValue || value > maxValue) {
    callback(new Error('请输入' + minValue + '~' + maxValue + '数字值！'));
  }
  callback();
}

const validateFloat = function (rule, value, callback) {
  let regFloat = /^[+-]?((0|([1-9]\d*))\.\d+)?$/;
  let regInt = /^\d+$/;
  if (regFloat.test(value) == false && regInt.test(value) == false) {
    callback(new Error("请输入数字值！"));
    return;
  }
  let minValue = parseFloat(rule.minValue) || 0;
  let maxValue = parseFloat(rule.maxValue) || 0;
  if (minValue === maxValue && minValue === 0) {
    callback();
    return;
  }
  if (value < minValue || value > maxValue) {
    callback(new Error('请输入' + minValue + '~' + maxValue + '数字值！'));
  }
  callback();
}

function isIntNum(val) {
  var regPos = / ^\d+$/; // 非负整数
  var regNeg = /^\-[1-9][0-9]*$/; // 负整数
  if (regPos.test(val) || regNeg.test(val)) {
    return true;
  } else {
    return false;
  }
}

/* 是否手机号码*/
function validateMobilePhone(rule, value, callback) {
  if (rule.required && !value) {
    callback(new Error('手机号码不能为空！'));
    return;
  }
  const reg = /^[1][3,4,5,7,8][0-9]{9}$/;
  if (value == '' || value == undefined || value == null) {
    callback();
  } else {
    if ((!reg.test(value)) && value != '') {
      callback(new Error('请输入正确的手机号码！'));
    } else {
      callback();
    }
  }
}

function validateAge(rule, value, callback) {
  var regAge = /^([0-9]|[0-9]{2}|100)$/;
  if (!rule.required && !value) {
    callback();
  }
  if (!regAge.test(value)) {
    callback(new Error('请输入正确的年龄！'));
  }
  callback();
}

export {
  validatePhone,
  validateNumber,
  validateMobilePhone,
  validateAge,
  validateFloat
}
