import { createLocalVue } from '@vue/test-utils';
import * as sinon from 'sinon';
import VueRouter from 'vue-router';
import { Subject } from 'rxjs';

import TrackerService from '@/admin/tracker/tracker.service';
import * as config from '@/shared/config/config';
import { defaultAccountState } from '@/shared/config/store/account-store';

const localVue = createLocalVue();
const store = config.initVueXStore(localVue);

describe('Tracker Service', () => {
  let trackerService: TrackerService;
  let routerStub: any;

  let mockStomp: any;

  beforeEach(() => {
    store.replaceState({
      ...store.state,
      accountStore: { ...defaultAccountState },
    });
    routerStub = sinon.createStubInstance<VueRouter>(VueRouter);
    sinon.stub(routerStub, 'currentRoute').get(() => ({ fullPath: '/' }));
    routerStub.afterEach = sinon.spy();

    trackerService = new TrackerService(routerStub, store, { get: jest.fn() });

    const watch$ = new Subject<any>();

    mockStomp = {
      publish: sinon.stub(),
      watch: sinon.stub().callsFake(() => watch$),
      configure: sinon.stub(),
      activate: sinon.stub(),
      deactivate: sinon.stub(),
      connected$: new Subject<any>(),
    };

    trackerService.stomp = mockStomp;
    trackerService['buildUrl'] = () => '';
  });

  it('Should subscribe router activity', async () => {
    expect(routerStub.afterEach.calledOnce).toBeTruthy();
  });

  it('Should call activate on authenticated', async () => {
    // WHEN
    store.commit('authenticated', {});
    await localVue.nextTick();

    // THEN
    expect(mockStomp.activate.calledOnce).toBeTruthy();
  });

  it('Should send activity on connected', async () => {
    // GIVEN
    sinon.stub(routerStub, 'currentRoute').get(() => ({ fullPath: '/admin' }));

    // WHEN
    mockStomp.connected$.next({});

    // THEN
    expect(mockStomp.publish.callCount).toBe(1);
    expect(
      mockStomp.publish.calledOnceWithExactly({ destination: '/topic/activity', body: JSON.stringify({ page: '/admin' }) })
    ).toBeTruthy();
  });

  it('Should disconnect on logout', async () => {
    // GIVEN
    store.commit('authenticated', {});
    await localVue.nextTick();

    // WHEN
    store.commit('logout');
    await localVue.nextTick();

    // THEN
    expect(mockStomp.deactivate.calledOnce).toBeTruthy();
  });
});
