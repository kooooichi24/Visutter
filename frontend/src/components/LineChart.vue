<script lang="ts">
import Vue from 'vue';
import { Line, mixins } from 'vue-chartjs'

export type DataType = {
  chartdata: ChartData;
  options: Options;
}

export type Options = {
  responsive: boolean;
  maintainAspectRatio: boolean;
}

export type ChartData = {
  datacollection: DataCollection;
}

export type DataCollection = {
  labels: string[];
  datasets: DataSets[];
}

export type DataSets = {
  label: string;
  backgroundColor: string;
  data: number[];
}

export default Vue.extend({
  name: 'LineChart',
  extends: Line,
  mixins: [mixins.reactiveProp],
  data(): DataType {
    return {
      chartdata: {
        datacollection: {
          labels: ['January', 'February'],
          datasets: [
            {
              label: 'Data One',
              backgroundColor: '#f87979',
              data: [40, 20]
            },
          ],
        },
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
      },
    };
  },
  mounted(): void {
    this.renderChart(this.chartdata, this.options);
  }
});
</script>

<style>
</style>