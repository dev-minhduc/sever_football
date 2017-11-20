import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Ns_Match_Schedule e2e test', () => {

    let navBarPage: NavBarPage;
    let ns_Match_ScheduleDialogPage: Ns_Match_ScheduleDialogPage;
    let ns_Match_ScheduleComponentsPage: Ns_Match_ScheduleComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Ns_Match_Schedules', () => {
        navBarPage.goToEntity('ns-match-schedule');
        ns_Match_ScheduleComponentsPage = new Ns_Match_ScheduleComponentsPage();
        expect(ns_Match_ScheduleComponentsPage.getTitle()).toMatch(/Ns Match Schedules/);

    });

    it('should load create Ns_Match_Schedule dialog', () => {
        ns_Match_ScheduleComponentsPage.clickOnCreateButton();
        ns_Match_ScheduleDialogPage = new Ns_Match_ScheduleDialogPage();
        expect(ns_Match_ScheduleDialogPage.getModalTitle()).toMatch(/Create or edit a Ns Match Schedule/);
        ns_Match_ScheduleDialogPage.close();
    });

    it('should create and save Ns_Match_Schedules', () => {
        ns_Match_ScheduleComponentsPage.clickOnCreateButton();
        ns_Match_ScheduleDialogPage.setTeam1Input('team1');
        expect(ns_Match_ScheduleDialogPage.getTeam1Input()).toMatch('team1');
        ns_Match_ScheduleDialogPage.setTeam2Input('team2');
        expect(ns_Match_ScheduleDialogPage.getTeam2Input()).toMatch('team2');
        ns_Match_ScheduleDialogPage.setThumbnail1Input('thumbnail1');
        expect(ns_Match_ScheduleDialogPage.getThumbnail1Input()).toMatch('thumbnail1');
        ns_Match_ScheduleDialogPage.setThumbnail2Input('thumbnail2');
        expect(ns_Match_ScheduleDialogPage.getThumbnail2Input()).toMatch('thumbnail2');
        ns_Match_ScheduleDialogPage.getSatusInput().isSelected().then(function (selected) {
            if (selected) {
                ns_Match_ScheduleDialogPage.getSatusInput().click();
                expect(ns_Match_ScheduleDialogPage.getSatusInput().isSelected()).toBeFalsy();
            } else {
                ns_Match_ScheduleDialogPage.getSatusInput().click();
                expect(ns_Match_ScheduleDialogPage.getSatusInput().isSelected()).toBeTruthy();
            }
        });
        ns_Match_ScheduleDialogPage.save();
        expect(ns_Match_ScheduleDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class Ns_Match_ScheduleComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-ns-match-schedule div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class Ns_Match_ScheduleDialogPage {
    modalTitle = element(by.css('h4#myNs_Match_ScheduleLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    team1Input = element(by.css('input#field_team1'));
    team2Input = element(by.css('input#field_team2'));
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
