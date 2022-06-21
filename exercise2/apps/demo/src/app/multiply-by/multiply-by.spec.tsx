import { render } from '@testing-library/react';

import MultiplyBy from './multiply-by';

describe('MultiplyBy', () => {
  it('should render successfully', () => {
    const { baseElement } = render(<MultiplyBy />);
    expect(baseElement).toBeTruthy();
  });
});
