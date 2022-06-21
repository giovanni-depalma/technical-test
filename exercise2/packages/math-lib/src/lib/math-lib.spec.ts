import { valueToArray } from './helper';
import {
  multiplyBySum,
  sumByArray,
  multiplyBySumWithBigValue,
  factorial,
} from './math-lib';

const FACT_10 = valueToArray('3628800');
const FACT_17 = valueToArray('355687428096000');
const FACT_18 = valueToArray('6402373705728000');
const FACT_25 = valueToArray('15511210043330985984000000');
const FACT_100 = valueToArray(
  '93326215443944152681699238856266700490715968264381621468592963895217599993229915608941463976156518286253697920827223758251185210916864000000000000000000000000'
);

describe('factorial', () => {
  it('should factorial', () => {
    expect(factorial(1)).toEqual([1]);
  });

})


describe('factorial', () => {

  it('should factorial', () => {
    expect(factorial(0)).toEqual([1]);
  });

  it('should factorial', () => {
    expect(factorial(1)).toEqual([1]);
  });

  it('should factorial', () => {
    expect(factorial(2)).toEqual([2]);
  });

  it('should factorial', () => {
    expect(factorial(10)).toEqual(FACT_10);
  });

  it('should factorial', () => {
    expect(factorial(18)).toEqual(FACT_18);
  });

  it('should factorial', () => {
    expect(factorial(25)).toEqual(FACT_25);
    expect(factorial(25)).toEqual(FACT_25);
  });

  it('should factorial x', () => {
    expect(factorial(100)).toEqual(FACT_100);
  });

  
});

describe('multiplyBySumWithBigValue', () => {
  expect(multiplyBySumWithBigValue(18, FACT_17)).toEqual(FACT_18);
});


describe('multiplyBySum', () => {
  it('should work multiply by 0', () => {
    expect(multiplyBySum(15, 0)).toEqual(0);
  });

  it('should work multiply by 0 inverted', () => {
    expect(multiplyBySum(0, 15)).toEqual(0);
  });

  it('should work multiply by 1', () => {
    expect(multiplyBySum(15, 1)).toEqual(15);
  });

  it('should work multiply by 1 inverted', () => {
    expect(multiplyBySum(1, 15)).toEqual(15);
  });

  it('should work multiply', () => {
    expect(multiplyBySum(15, 2)).toEqual(30);
  });

  it('should work', () => {
    expect(multiplyBySum(15, 10)).toEqual(150);
  });

  it('should work', () => {
    expect(multiplyBySum(100, 99)).toEqual(9900);
  });
});

describe('sumByArray', () => {
  it('should work sumByArray', () => {
    expect(sumByArray([5, 1], [5, 1])).toEqual([0, 3]);
  });

  it('should work sumByArray', () => {
    expect(sumByArray([5, 1], [2])).toEqual([7, 1]);
  });

  it('should work sumByArray', () => {
    expect(sumByArray([8, 9], [8, 9])).toEqual([6, 9, 1]);
  });
});
