import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Ns_Rank e2e test', () => {

    let navBarPage: NavBarPage;
    let ns_RankDialogPage: Ns_RankDialogPage;
    let ns_RankComponentsPage: Ns_RankComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ns_Ranks', () => {
        navBarPage.goToEntity('ns-rank');
        ns_RankComponentsPage = new Ns_RankComponentsPage();
        expect(ns_RankComponentsPage.getTitle()).toMatch(/Ns Ranks/);

    });

    it('should load create Ns_Rank dialog', () => {
        ns_RankComponentsPage.clickOnCreateButton();
        ns_RankDialogPage = new Ns_RankDialogPage();
        expect(ns_RankDialogPage.getModalTitle()).toMatch(/Create or edit a Ns Rank/);
        ns_RankDialogPage.close();
    });

    it('should create and save Ns_Ranks', () => {
        ns_RankComponentsPage.clickOnCreateButton();
        ns_RankDialogPage.setNameInput('name');
        expect(ns_RankDialogPage.getNameInput()).toMatch('name');
        ns_RankDialogPage.setThumbnailInput('thumbnail');
        expect(ns_RankDialogPage.getThumbnailInput()).toMatch('thumbnail');
        ns_RankDialogPage.setBattleInput('5');
        expect(ns_RankDialogPage.getBattleInput()).toMatch('5');
        ns_RankDialogPage.setWinInput('5');
        expect(ns_RankDialogPage.getWinInput()).toMatch('5');
        ns_RankDialogPage.setDrawInput('5');
        expect(ns_RankDialogPage.getDrawInput()).toMatch('5');
        ns_RankDialogPage.setLoseInput('5');
        expect(ns_RankDialogPage.getLoseInput()).toMatch('5');
        ns_RankDialogPage.setDiffInput('diff');
        expect(ns_RankDialogPage.getDiffInput()).toMatch('diff');
        ns_RankDialogPage.setPointInput('5');
        expect(ns_RankDialogPage.getPointInput()).toMatch('5');
        ns_RankDialogPage.getSatusInput().isSelected().then(function (selected) {
            if (selected) {
                ns_RankDialogPage.getSatusInput().click();
                expect(ns_RankDialogPage.getSatusInput().isSelected()).toBeFalsy();
            } else {
                ns_RankDialogPage.getSatusInput().click();
                expect(ns_RankDialogPage.getSatusInput().isSelected()).toBeTruthy();
            }
        });
        ns_RankDialogPage.save();
        expect(ns_RankDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class Ns_RankComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ns-rank div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class Ns_RankDialogPage {
    modalTitle = element(by.css('h4#myNs_RankLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    nameInput = element(by.css('input#field_name'));
    thumbnailInput = element(by.css('input#field_thumbnail'));
    battleInput = element(by.css('input#field_battle'));
    winInput = element(by.css('input#field_win'));
    drawInput = element(by.css('input#field_draw'));
    loseInput = element(by.css('input#field_lose'));
    diffInput = element(by.css('input#field_diff'));
    pointInput = element(by.css('input#field_point'));
    satusInput = element(by.css('input#field_satus'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    setThumbnailInput = function (thumbnail) {
        this.thumbnailInput.sendKeys(thumbnail);
    }

    getThumbnailInput = function () {
        return this.thumbnailInput.getAttribute('value');
    }

    setBattleInput = function (battle) {
        this.battleInput.sendKeys(battle);
    }

    getBattleInput = function () {
        return this.battleInput.getAttribute('value');
    }

    setWinInput = function (win) {
        this.winInput.sendKeys(win);
    }

    getWinInput = function () {
        return this.winInput.getAttribute('value');
    }

    setDrawInput = function (draw) {
        this.drawInput.sendKeys(draw);
    }

    getDrawInput = function () {
        return this.drawInput.getAttribute('value');
    }

    setLoseInput = function (lose) {
        this.loseInput.sendKeys(lose);
    }

    getLoseInput = function () {
        return this.loseInput.getAttribute('value');
    }

    setDiffInput = function (diff) {
        this.diffInput.sendKeys(diff);
    }

    getDiffInput = function () {
        return this.diffInput.getAttribute('value');
    }

    setPointInput = function (point) {
        this.pointInput.sendKeys(point);
    }

    getPointInput = function () {
        return this.pointInput.getAttribute('value');
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
