import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICountry } from '@/shared/model/country.model';

import CountryService from './country.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Country extends Vue {
  @Inject('countryService') private countryService: () => CountryService;
  @Inject('alertService') private alertService: () => AlertService;

  public currentSearch = '';
  private removeId: number = null;

  public countries: ICountry[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCountrys();
  }

  public search(query): void {
    if (!query) {
      return this.clear();
    }
    this.currentSearch = query;
    this.retrieveAllCountrys();
  }

  public clear(): void {
    this.currentSearch = '';
    this.retrieveAllCountrys();
  }

  public retrieveAllCountrys(): void {
    this.isFetching = true;
    if (this.currentSearch) {
      this.countryService()
        .search(this.currentSearch)
        .then(
          res => {
            this.countries = res;
            this.isFetching = false;
          },
          err => {
            this.isFetching = false;
            this.alertService().showHttpError(this, err.response);
          }
        );
      return;
    }
    this.countryService()
      .retrieve()
      .then(
        res => {
          this.countries = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: ICountry): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCountry(): void {
    this.countryService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('appApp.country.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCountrys();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
