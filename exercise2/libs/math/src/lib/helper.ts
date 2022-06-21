export function getDigitFromArray(val: Array<number>, idx: number): number {
  return val.length > idx ? val[idx] : 0;
}

export function numberToArray(val: number): Array<number> {
  return valueToArray(val.toString());
}

export function valueToArray(val: string): Array<number> {
  return val.split('').map(Number).reverse();
}

export function arrayToNumber(val: Array<number>): number {
  return Number(val.reverse().join(''));
}
