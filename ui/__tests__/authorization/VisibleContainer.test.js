import 'jsdom-global/register';
import { configure, mount } from 'enzyme';
import React from 'react';
import { VisibleContainer } from '../../components/authorization/VisibleContainer';
import Adapter from '@wojtekmaj/enzyme-adapter-react-17';

test('<VisibleContainer />', () => {
  configure({ adapter: new Adapter() });

  const notVisibleContainer = mount(
    <VisibleContainer condition={false}>
      <a>test</a>
    </VisibleContainer>
  );
  expect(notVisibleContainer.containsMatchingElement(<a />)).toEqual(false);

  const visibleContainer = mount(
    <VisibleContainer condition={true}>
      <a>test</a>
    </VisibleContainer>
  );
  expect(visibleContainer.containsMatchingElement(<a>test</a>)).toEqual(true);
});
