import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Ns_Result e2e test', () => {

    let navBarPage: NavBarPage;
    let ns_ResultDialogPage: Ns_ResultDialogPage;
    let ns_ResultComponentsPage: Ns_ResultComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ns_Results', () => {
        navBarPage.goToEntity('ns-result');
        ns_ResultComponentsPage = new Ns_ResultComponentsPage();
        expect(ns_ResultComponentsPage.getTitle()).toMatch(/Ns Results/);

    });

    it('should load create Ns_Result dialog', () => {
        ns_ResultComponentsPage.clickOnCreateButton();
        ns_ResultDialogPage = new Ns_ResultDialogPage();
        expect(ns_ResultDialogPage.getModalTitle()).toMatch(/Create or edit a Ns Result/);
        ns_ResultDialogPage.close();
    });

    it('should create and save Ns_Results', () => {
        ns_ResultComponentsPage.clickOnCreateButton();
        ns_ResultDialogPage.setTeam1Input('team1');
        expect(ns_ResultDialogPage.getTeam1Input()).toMatch('team1');
        ns_ResultDialogPage.setTeam2Input('team2');
        expect(ns_ResultDialogPage.getTeam2Input()).toMatch('team2');
        ns_ResultDialogPage.setResultInput('result');
        expect(ns_ResultDialogPage.getResultInput()).toMatch('result');
        ns_ResultDialogPage.setThumbnail1Input('thumbnail1');
        expect(ns_ResultDialogPage.getThumbnail1Input()).toMatch('thumbnail1');
        ns_ResultDialogPage.setThumbnail2Input('thumbnail2');
        expect(ns_ResultDialogPage.getThumbnail2Input()).toMatch('thumbnail2');
        ns_ResultDialogPage.getSatusInput().isSelected().then(function (selected) {
            if (selected) {
                ns_ResultDialogPage.getSatusInput().click();
                expect(ns_ResultDialogPage.getSatusInput().isSelected()).toBeFalsy();
            } else {
                ns_ResultDialogPage.getSatusInput().click();
                expect(ns_ResultDialogPage.getSatusInput().isSelected()).toBeTruthy();
            }
        });
        ns_ResultDialogPage.save();
        expect(ns_ResultDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class Ns_ResultComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ns-result div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class Ns_ResultDialogPage {
    modalTitle = element(by.css('h4#myNs_ResultLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    team1Input = element(by.css('input#field_team1'));
    team2Input = element(by.css('input#field_team2'));
    resultInput = element(by.css('input#field_result'));
    thumbnail1Input = element(by.css('input#field_thumbnail1'));
    thumbnail2Input = element(by.css('input#field_thumbnail2'));
    satusInput = element(by.css('input#field_satus'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setTeam1Input = function (team1) {
        this.team1Input.sendKeys(team1);
    }

    getTeam1Input = function () {
        return this.team1Input.getAttribute('value');
    }

    setTeam2Input = function (team2) {
        this.team2Input.sendKeys(team2);
    }

    getTeam2Input = function () {
        return this.team2Input.getAttribute('value');
    }

    setResultInput = function (result) {
        this.resultInput.sendKeys(result);
    }

    getResultInput = function () {
        return this.resultInput.getAttribute('value');
    }

    setThumbnail1Input = function (thumbnail1) {
        this.thumbnail1Input.sendKeys(thumbnail1);
    }

    getThumbnail1Input = function () {
        return this.thumbnail1Input.getAttribute('value');
    }

    setThumbnail2Input = function (thumbnail2) {
        this.thumbnail2Input.sendKeys(thumbnail2);
    }

    getThumbnail2Input = function () {
        return this.thumbnail2Input.getAttribute('value');
    }

    getSatusInput = function () {
        return this.satusInput;
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
