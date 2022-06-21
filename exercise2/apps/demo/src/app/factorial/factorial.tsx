import { useCallback, useMemo, useState } from 'react';
import styled from 'styled-components';
import {factorial as calcFactorial} from '@exercise2/math'

/* eslint-disable-next-line */
export interface FactorialProps {}

const StyledMathUi = styled.div`
  & input[type='range'] {
    width: 400px;
  }

  & label {
    display: block;
  }
`;

const DEFAULT_FACTORIAL = 1;

export function Factorial(props: FactorialProps) {
  const [factorialOf, setFactorial] = useState(DEFAULT_FACTORIAL);
  const onChangeFactorial = useCallback(
    (event: React.ChangeEvent<HTMLInputElement>) => {
      setFactorial(Number(event.target.value));
    },
    []
  );
  const result = useMemo(() => [...calcFactorial(factorialOf)].reverse(), [factorialOf]);
  return (
    <StyledMathUi>
      <h2>Factorial</h2>
      <label htmlFor="factorialValue">{`Factorial of ${factorialOf}`}</label>
      <input
        type="range"
        name="factorialValue"
        max={100}
        min={0}
        defaultValue={factorialOf}
        onChange={onChangeFactorial}
      />
      <p>Result: {result}</p>
    </StyledMathUi>
  );
}

export default Factorial;
