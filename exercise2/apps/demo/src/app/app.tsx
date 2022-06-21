import styled from 'styled-components';
import Factorial from './factorial/factorial';
import MultiplyBy from './multiply-by/multiply-by';

const StyledApp = styled.div`
`;

export function App() {
  return (
    <StyledApp>
      <div>
        <h1>
          Welcome to Demo Exercise2!
        </h1>
      </div>
      <div>
        <Factorial />
        <MultiplyBy />
      </div>
    </StyledApp>
  );
}

export default App;
