import { useCallback, useMemo, useState } from 'react';
import styled from 'styled-components';
import { multiplyBySum } from '@exercise2/math';

/* eslint-disable-next-line */
export interface MultiplyByProps {}

const StyledMathUi = styled.div`
  & input[type='range'] {
    width: 400px;
  }

  & label {
    display: block;
  }
`;

const defaultValue1 = 15;
const defaultValue2 = 2;
const maxValue = 999;

function isAcceptableValue(value: number) {
  return value >= 0 && value <= maxValue;
}

export function MultiplyBy(props: MultiplyByProps) {
  const [value1, setValue1] = useState(defaultValue1);
  const [value2, setValue2] = useState(defaultValue2);
  const result = useMemo(() => multiplyBySum(value1, value2), [value1, value2]);
  const onChangeVal1 = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      const value = Number(event.target.value);
      setValue1(isAcceptableValue(value) ? value : defaultValue1);
    },
    []
  );
  const onChangeVal2 = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      const value = Number(event.target.value);
      setValue2(isAcceptableValue(value) ? value : defaultValue2);
    },
    []
  );
  return (
    <StyledMathUi>
      <h2>MultiplyBySum</h2>
      <div>
        <label htmlFor="val1">Val1 {value1}</label>
        <input
          width={1000}
          type="range"
          name="val1"
          max={maxValue}
          min={0}
          onChange={onChangeVal1}
          defaultValue={value1}
        />
      </div>
      <div>
        <label htmlFor="val2">Val2 {value1}</label>
        <input
          type="range"
          name="val2"
          max={maxValue}
          min={0}
          onChange={onChangeVal2}
          defaultValue={value2}
        />
      </div>
      <p>{result}</p>
    </StyledMathUi>
  );
}

export default MultiplyBy;
