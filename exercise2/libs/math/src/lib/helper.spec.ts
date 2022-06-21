import {
  getDigitFromArray, arrayToNumber, numberToArray, valueToArray
} from './helper';

describe('getDigitFromArray', () => {
  it('should get digit', () => {
    expect(getDigitFromArray([2,3,0],0)).toEqual(2);
  });

  it('should get digit', () => {
    expect(getDigitFromArray([2, 3, 0], 1)).toEqual(3);
  });

  it('should get digit', () => {
    expect(getDigitFromArray([2, 3, 0], 2)).toEqual(0);
  });
});

describe('numberToArray', () => {
  it('should work numberToArray', () => {
    expect(numberToArray(0)).toEqual([0]);
  });

  it('should work numberToArray', () => {
    expect(numberToArray(5)).toEqual([5]);
  });

  it('should work numberToArray', () => {
    expect(numberToArray(1789)).toEqual([9, 8, 7, 1]);
  });
});

describe('valueToArray', () => {
  it('should work valueToArray', () => {
    expect(valueToArray('0')).toEqual([0]);
  });

  it('should work valueToArray', () => {
    expect(valueToArray('5')).toEqual([5]);
  });

  it('should work valueToArray', () => {
    expect(valueToArray("1789")).toEqual([9, 8, 7, 1]);
  });
});

describe('arrayToNumber', () => {
  it('should work arrayToNumber', () => {
    expect(arrayToNumber([0])).toEqual(0);
  });

  it('should work arrayToNumber', () => {
    expect(arrayToNumber([5])).toEqual(5);
  });

  it('should work arrayToNumber', () => {
    expect(arrayToNumber([9, 8, 7, 1])).toEqual(1789);
  });
});