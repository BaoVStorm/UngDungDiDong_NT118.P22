let serverUrl = getUrlServer();

$(document).ready(function(){
    fetchData("api/certificate/getCertificateCount", $("#soLuotLamBaiThi"));
    setMostFailedTest("api/certificate/getMostFailedTest", $("#testKhongDatNhieuNhat"), $("#infoTestKhongDatNhieuNhat"));

    setPercent("api/user_answers/getCorrectPercent", $("#tyLeLamDung"));
    setPercent("api/certificate/getTestPassRate", $("#tyLeVuotQuaTest"));

    fetchTableListTest("api/certificate/getTop10LastestCertificates", $("#ListTestTable"));
    fetchTableTopUserByTotalScore("api/certificate/getTopUsersByTotalScore", $("#topUserByTotalScore"));

    fetchListTest("api/test/getTestAttemptCounts", $("#tableListTest"));

    fetchChartByWeek("api/certificate/getCertificateDoneByWeek", testChart);
    fetchLineChartByWeek("api/certificate/getTestCorrectPercentage", translateChart);

    fetchChartFullTest("api/certificate/getTestCountsByFullTest", markChart);

}); 

async function setMostFailedTest(urlAPI, $title, $info) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    // console.log(data);
    $title.text(data.certificate_name);
    $info.html(`<p class="mb-0 text-sm"><span class="text-danger font-weight-bolder"> ${data.failed_count} </span>Times</p>`)
  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function setPercent(urlAPI, $object) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    // console.log(data);
    $object.text(data + "%");
  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function fetchData(urlAPI, $object) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    // console.log(data);
    $object.text(data);
  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function fetchTableListTest(urlAPI, $chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    // console.log(data);

    $chart.html("");

    for(i in data) {
        row = data[i];
        // console.log(row["certificate_name"]);

        $chart.append(`
            <tr>
                <td>
                <div class="d-flex px-2 py-1">
                    <h6 class="mb-0 text-sm" style="margin-left:25px">${row["certificate_name"]}</h6>
                </div>
                </td>
                <td>
                    <div style="display: flex; align-items: center; gap: 10px; margin-left:35px">
                        <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="tranvubao2004">
                            <img src="../assets/img/avatar.png" alt="team1">
                        </a>

                        <span class="text-xs font-weight-bold" style="margin-top: 3px;">${row["email"]}</span>
                    </div>
                </td>
                <td class="align-middle text-center text-sm">
                <span class="text-xs font-weight-bold"> ${row["awarded_at"]} </span>
                </td>
                <td class="align-middle">
                <div class="progress-wrapper w-75 mx-auto">
                    <div class="progress-info">
                    <div class="progress-percentage">
                        <span class="text-xs font-weight-bold">${row["completion_rate"]}%</span>
                    </div>
                    </div>
                    <div class="progress">
                    <div class="progress-bar bg-gradient-info" role="progressbar" aria-valuenow="${row["completion_rate"]}" aria-valuemin="0" aria-valuemax="100" style="width:${row["completion_rate"]}%"></div>
                    </div>
                </div>
                </td>
            </tr>
        `);
    }

  } catch (error) {
    console.error('Lỗi:', error);
  }
}


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

async function fetchChartFullTest(urlAPI, chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    // console.log(data);
    // $object.text(data);
    setChartData(chart, ["IsFullTest", "IsMiniTest"], [data.is_full_test, data.not_is_full_test]);
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

    const months = ["0-20", "21-40", "41-60", "61-80", "81-100"];
    // const testCounts = [15, 25, 10, 45, 30, 20, 50, 60, 30, 20, 40, 55];

    setLineChartData(chart, months, data);

  } catch (error) {
    console.error('Lỗi:', error);
  }
}


async function fetchTableTopUserByTotalScore(urlAPI, $chart) {
  try {
    const response = await fetch(serverUrl + urlAPI + "?number=10");
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    // console.log(data);

    $chart.html("");

    STT = 0;

    for(i in data) {
        row = data[i];
        // console.log(row["certificate_name"]);
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
              <td>
          
                <div class="justify-content-center" style="display: flex; align-items: center; gap: 10px;">
                    <a href="javascript:;" class="avatar avatar-xs rounded-circle" data-bs-toggle="tooltip" data-bs-placement="bottom" title="tranvubao2004">
                      <img src="../assets/img/avatar.png" alt="team1">
                    </a>

                    <span class="text-xs font-weight-bold" style="margin-top: 3px;">${row.full_name}</span>
                </div>

              </td>
              <td class="align-middle text-center text-sm">
                <span class="text-xs font-weight-bold">${row.email}</span>
              </td>
              <td class="align-middle text-center">
                <span class="text-xs font-weight-bold">${row.total_score}</span>
              </td>
            </tr>
        `);
    }

  } catch (error) {
    console.error('Lỗi:', error);
  }
}

async function fetchListTest(urlAPI, $chart) {
  try {
    const response = await fetch(serverUrl + urlAPI);
    if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();

    // console.log(data);

    $chart.html("");
    STT = 0;

    for(i in data) {
        row = data[i];
        // console.log(row["certificate_name"]);
        STT++;

        $chart.append(`
            <tr>
              <td>
                <div class="d-flex px-2" style="margin-left: 15px;">
                  <p class="text-xs font-weight-bold mb-0">${STT}</p>
                </div>
              </td>
              <td>
                <h6 class="mb-0 text-sm">${row.title}</h6>         
              </td>
              <td class="align-middle text-center">
                <span class="text-xs font-weight-bold"">${row.question_number}</span>
              </td>
              <td class="align-middle text-center">
                <span class="me-2 text-xs font-weight-bold">${row.question_number}</span>
              </td>
              
              <!-- 
              <td class="align-middle text-center">
                <span class="me-2 text-xs font-weight-bold">${row.is_full_test}</span>
              </td>
              -->
              <td class="align-middle text-center">
                <span class="badge bg-${row.is_full_test ? 'success' : 'secondary'} rounded-pill px-3 py-2 text-white">
                  ${row.is_full_test ? 'Full Test' : 'Mini Test'}
                </span>
              </td>

              <td class="align-middle text-center">
                <span class="me-2 text-xs font-weight-bold">${row.attempt_count}</span>
              </td> 
              
              <td class="align-middle">
                <button class="btn btn-link text-secondary mb-0">
                  <i class="fa fa-ellipsis-v text-xs"></i>
                </button>
              </td>
            </tr>
        `);
    }

  } catch (error) {
    console.error('Lỗi:', error);
  }
}