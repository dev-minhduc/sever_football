import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Ns_Highlight e2e test', () => {

    let navBarPage: NavBarPage;
    let ns_HighlightDialogPage: Ns_HighlightDialogPage;
    let ns_HighlightComponentsPage: Ns_HighlightComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ns_Highlights', () => {
        navBarPage.goToEntity('ns-highlight');
        ns_HighlightComponentsPage = new Ns_HighlightComponentsPage();
        expect(ns_HighlightComponentsPage.getTitle()).toMatch(/Ns Highlights/);

    });

    it('should load create Ns_Highlight dialog', () => {
        ns_HighlightComponentsPage.clickOnCreateButton();
        ns_HighlightDialogPage = new Ns_HighlightDialogPage();
        expect(ns_HighlightDialogPage.getModalTitle()).toMatch(/Create or edit a Ns Highlight/);
        ns_HighlightDialogPage.close();
    });

    it('should create and save Ns_Highlights', () => {
        ns_HighlightComponentsPage.clickOnCreateButton();
        ns_HighlightDialogPage.setTitleInput('title');
        expect(ns_HighlightDialogPage.getTitleInput()).toMatch('title');
        ns_HighlightDialogPage.setImgInput('img');
        expect(ns_HighlightDialogPage.getImgInput()).toMatch('img');
        ns_HighlightDialogPage.setLinkInput('link');
        expect(ns_HighlightDialogPage.getLinkInput()).toMatch('link');
        ns_HighlightDialogPage.setDateInput('2000-12-31');
        expect(ns_HighlightDialogPage.getDateInput()).toMatch('2000-12-31');
        ns_HighlightDialogPage.getStatusInput().isSelected().then(function (selected) {
            if (selected) {
                ns_HighlightDialogPage.getStatusInput().click();
                expect(ns_HighlightDialogPage.getStatusInput().isSelected()).toBeFalsy();
            } else {
                ns_HighlightDialogPage.getStatusInput().click();
                expect(ns_HighlightDialogPage.getStatusInput().isSelected()).toBeTruthy();
            }
        });
        ns_HighlightDialogPage.save();
        expect(ns_HighlightDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class Ns_HighlightComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ns-highlight div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class Ns_HighlightDialogPage {
    modalTitle = element(by.css('h4#myNs_HighlightLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    titleInput = element(by.css('input#field_title'));
    imgInput = element(by.css('input#field_img'));
    linkInput = element(by.css('input#field_link'));
    dateInput = element(by.css('input#field_date'));
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

    setImgInput = function (img) {
        this.imgInput.sendKeys(img);
    }

    getImgInput = function () {
        return this.imgInput.getAttribute('value');
    }

    setLinkInput = function (link) {
        this.linkInput.sendKeys(link);
    }

    getLinkInput = function () {
        return this.linkInput.getAttribute('value');
    }

    setDateInput = function (date) {
        this.dateInput.sendKeys(date);
    }

    getDateInput = function () {
        return this.dateInput.getAttribute('value');
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
