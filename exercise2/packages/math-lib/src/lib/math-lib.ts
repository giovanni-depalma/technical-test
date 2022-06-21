import { getDigitFromArray, numberToArray, arrayToNumber } from './helper';

const memoized: Array<Array<number>> = [];
export function factorial(val: number): Array<number> {
  if (memoized[val]) {
    return memoized[val];
  } else {
    let ret = [1];
    for (let i = 1; i <= val; i++) {
      ret = multiplyBySumWithBigValue(i, ret);
    }
    memoized[val] = ret;
    return ret;
  }
}

export function multiplyBySum(val1: number, val2: number): number {
  const values = val2 >= val1 ? [val1, val2] : [val2, val1];
  const ret = multiplyBySumWithBigValue(values[0], numberToArray(values[1]));
  return arrayToNumber(ret);
}

export function multiplyBySumWithBigValue(
  value: number,
  bigValue: Array<number>
): Array<number> {
  let ret = [0];
  for (let i = 0; i < value; i++) {
    ret = sumByArray(ret, bigValue);
  }
  return ret;
}

export function sumByArray(
  val1: Array<number>,
  val2: Array<number>
): Array<number> {
  const maxLength = Math.max(val1.length, val2.length);
  const result: Array<number> = [];
  let remain = 0;
  for (let i = 0; i < maxLength; i++) {
    let digit1 = getDigitFromArray(val1, i);
    let digit2 = getDigitFromArray(val2, i);
    let sum = digit1 + digit2 + remain;
    let digitSum = sum % 10;
    result.push(digitSum);
    remain = sum > digitSum ? 1 : 0;
  }
  if (remain > 0) result.push(remain);
  return result;
}
