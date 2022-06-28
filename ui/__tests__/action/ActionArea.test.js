import { configure, shallow } from 'enzyme';
import React from 'react';
import renderer from 'react-test-renderer';
import ActionArea from '../../components/action/ActionArea';
import Adapter from '@wojtekmaj/enzyme-adapter-react-17';
import { Button } from 'react-bootstrap';

test('<ActionArea />', () => {
  configure({ adapter: new Adapter() });

  const mockCallBack = jest.fn();

  const component = (
    <ActionArea
      className="m-3"
      title="test"
      description="test"
      actionText="test"
      url="newkuda"
      direction="right"
      action={mockCallBack}
    ></ActionArea>
  );
  const componentRender = renderer.create(component);
  let tree = componentRender.toJSON();
  expect(tree).toMatchSnapshot();

  const componentShallow = shallow(component);
  componentShallow.children().at(0).simulate('click');
});
