let serverUrl = getUrlServer();

$(document).ready(function(){

    fetchChartByWeek("api/look_up_history/getLookupDoneByWeek", lookupChart);

    // fetchLineChartByWeek("api/look_up_history/getLookupDoneByWeek", translateChart);
    // fetchLineChartByWeek("api/user_star/getUserStarCountByWeek", markChart);

    fetchTableTopUser("api/look_up_history/getTopUsersByLookupCount", $("#topUsersByLookup"));
    fetchTableTopWord("api/look_up_history/getMostLookedUpWords?number=10", $("#topLookupWords"));
    fetchTableLastestHistory("api/look_up_history/getAllLookupHistorySorted?number=10", $("#getLastestLookupHistory"));
}); 

async function fetchTableTopUser(urlAPI, $chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    // console.log(data);

    $chart.html("");
    let STT = 0;

    for(i in data) {
        row = data[i];

        STT++;

        $chart.append(`
            <tr>
              <td>
                <div class="d-flex px-2 py-1 justify-content-center">
                  <!-- <div>
                    <img src="../assets/img/avatar.png" class="avatar avatar-sm me-3" alt="xd">
                  </div> -->
                  <div class="d-flex flex-column justify-content-center ">
                    <h6 class="mb-0 text-sm ">${STT}</h6>
                  </div>
                </div>
              </td>
              <td>
          
                <div style="display: flex; align-items: center; gap: 10px; margin-left:20px">
                    <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="tranvubao2004">
                      <img src="../assets/img/avatar.png" alt="team1">
                    </a>

                    <span class="text-xs font-weight-bold" style="margin-top: 3px;">${row["full_name"]}</span>
                </div>

              </td>
              <td class="align-middle  text-sm" >
                <span class="text-xs font-weight-bold" style="margin-left:10px;">${row["email"]}</span>
              </td>
              <td class="align-middle text-center">
                <span class="text-xs font-weight-bold">${row["lookup_count"]}</span>
              </td>
            </tr>
        `);
    }

  } catch (error) {
    console.error('Lỗi:', error);
  }
}


async function fetchTableTopWord(urlAPI, $chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    // console.log(data);

    $chart.html("");
    let STT = 0;

    for(i in data) {
        row = data[i];

        STT++;

        $chart.append(`
            <tr>
              <td>
                <div class="d-flex px-2 py-1 justify-content-center">
                  <!-- <div>
                    <img src="../assets/img/small-logos/logo-xd.svg" class="avatar avatar-sm me-3" alt="xd">
                  </div> -->
                  <div class="d-flex flex-column justify-content-center ">
                    <h6 class="mb-0 text-sm ">${STT}</h6>
                  </div>
                </div>
              </td>
              <td class="align-middle">
                <span class="text-xs font-weight-bold" style="padding-left: 15px;">${row["word"]}</span>
              </td>

              <td class="align-middle text-center">
                <span class="text-xs font-weight-bold " >${row["lookup_count"]}</span>
              </td>
            </tr>
        `);
    }

  } catch (error) {
    console.error('Lỗi:', error);
  }
}


async function fetchTableLastestHistory(urlAPI, $chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    // console.log(data);

    $chart.html("");
    let STT = 0;

    for(i in data) {
        row = data[i];

        STT++;

        $chart.append(`
            <tr>
              <td>
                <div class="d-flex px-2 py-1 ">
                  <!-- <div>
                    <img src="../assets/img/small-logos/logo-xd.svg" class="avatar avatar-sm me-3" alt="xd">
                  </div> -->
                  <div class="d-flex flex-column justify-content-center ">
                    <h6 class="mb-0 text-sm " style="margin-left: 7px;">${row["word"]}</h6>
                  </div>
                </div>
              </td>
              <td class="align-middle  text-sm">
          
                <span class="text-xs font-weight-bold" style="margin-top: 3px;">${row["meaning"]}</span>

              </td>
              <td class="align-middle text-center text-sm">
                <span class="text-xs font-weight-bold">${(row["isTranslateEnglish"] ? "English" : "VietNamese")}</span>
              </td>

              <td class="align-middle text-sm">
                <span class="text-xs font-weight-bold">${row["email"]}</span>
              </td>

              <td class="align-middle ">
                <span class="text-xs font-weight-bold">${row["lookup_at_formatted"]}</span>
              </td>
            </tr>
        `);
    }

  } catch (error) {
    console.error('Lỗi:', error);
  }
}


    // setChartData(testChart, ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"], [10, 20, 5, 12, 18, 30, 22]);

async function fetchChartByWeek(urlAPI, chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    // console.log(data);
    // $object.text(data);
    setChartData(chart, ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"], data);
  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function fetchLineChartByWeek(urlAPI, chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    const months = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"];
    // const testCounts = [15, 25, 10, 45, 30, 20, 50, 60, 30, 20, 40, 55];

    setLineChartData(chart, months, data);

  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function fetchLineChartByMonth(urlAPI, chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    const months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
    const testCounts = [15, 25, 10, 45, 30, 20, 50, 60, 30, 20, 40, 55];

    setLineChartData(chart, months, testCounts);

  } catch (error) {
    console.error('Lỗi:', error);
  }
}