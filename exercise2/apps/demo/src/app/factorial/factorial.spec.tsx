import { render } from '@testing-library/react';

import Factorial from './factorial';

describe('Factorial', () => {
  it('should render successfully', () => {
    const { baseElement } = render(<Factorial />);
    expect(baseElement).toBeTruthy();
  });
});
