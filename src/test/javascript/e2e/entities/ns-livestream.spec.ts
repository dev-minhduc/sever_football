import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Ns_Livestream e2e test', () => {

    let navBarPage: NavBarPage;
    let ns_LivestreamDialogPage: Ns_LivestreamDialogPage;
    let ns_LivestreamComponentsPage: Ns_LivestreamComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ns_Livestreams', () => {
        navBarPage.goToEntity('ns-livestream');
        ns_LivestreamComponentsPage = new Ns_LivestreamComponentsPage();
        expect(ns_LivestreamComponentsPage.getTitle()).toMatch(/Ns Livestreams/);

    });

    it('should load create Ns_Livestream dialog', () => {
        ns_LivestreamComponentsPage.clickOnCreateButton();
        ns_LivestreamDialogPage = new Ns_LivestreamDialogPage();
        expect(ns_LivestreamDialogPage.getModalTitle()).toMatch(/Create or edit a Ns Livestream/);
        ns_LivestreamDialogPage.close();
    });

    it('should create and save Ns_Livestreams', () => {
        ns_LivestreamComponentsPage.clickOnCreateButton();
        ns_LivestreamDialogPage.setTitleInput('title');
        expect(ns_LivestreamDialogPage.getTitleInput()).toMatch('title');
        ns_LivestreamDialogPage.setLinkInput('link');
        expect(ns_LivestreamDialogPage.getLinkInput()).toMatch('link');
        ns_LivestreamDialogPage.setThumb1Input('thumb1');
        expect(ns_LivestreamDialogPage.getThumb1Input()).toMatch('thumb1');
        ns_LivestreamDialogPage.setThumb2Input('thumb2');
        expect(ns_LivestreamDialogPage.getThumb2Input()).toMatch('thumb2');
        ns_LivestreamDialogPage.getStatusInput().isSelected().then(function (selected) {
            if (selected) {
                ns_LivestreamDialogPage.getStatusInput().click();
                expect(ns_LivestreamDialogPage.getStatusInput().isSelected()).toBeFalsy();
            } else {
                ns_LivestreamDialogPage.getStatusInput().click();
                expect(ns_LivestreamDialogPage.getStatusInput().isSelected()).toBeTruthy();
            }
        });
        ns_LivestreamDialogPage.save();
        expect(ns_LivestreamDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class Ns_LivestreamComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ns-livestream div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class Ns_LivestreamDialogPage {
    modalTitle = element(by.css('h4#myNs_LivestreamLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    linkInput = element(by.css('input#field_link'));
    thumb1Input = element(by.css('input#field_thumb1'));
    thumb2Input = element(by.css('input#field_thumb2'));
    statusInput = element(by.css('input#field_status'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setTitleInput = function (title) {
        this.titleInput.sendKeys(title);
    }

    getTitleInput = function () {
        return this.titleInput.getAttribute('value');
    }

    setLinkInput = function (link) {
        this.linkInput.sendKeys(link);
    }

    getLinkInput = function () {
        return this.linkInput.getAttribute('value');
    }

    setThumb1Input = function (thumb1) {
        this.thumb1Input.sendKeys(thumb1);
    }

    getThumb1Input = function () {
        return this.thumb1Input.getAttribute('value');
    }

    setThumb2Input = function (thumb2) {
        this.thumb2Input.sendKeys(thumb2);
    }

    getThumb2Input = function () {
        return this.thumb2Input.getAttribute('value');
    }

    getStatusInput = function () {
        return this.statusInput;
    }
    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
