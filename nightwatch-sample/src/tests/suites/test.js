const commonHooks = require('../../../conf/hooks');

describe('Product Tests', () => {
  this.tags = ['product'];

  beforeEach(commonHooks.beforeEach);

  afterEach(commonHooks.afterEach);

  it('Apple And Samsung Filter', (browser) => {
    browser.expect.elements('.shelf-item__title').count.to.equal(25);

    browser
      .click("input[value='Apple'] + span")
      .click("input[value='Samsung'] + span")
      .expect.elements('.shelf-item')
      .count.to.equal(16);
  });

  after(commonHooks.after);
});
