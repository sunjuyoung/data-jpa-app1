async function uploadToServer(formObj) {

    console.log("upload to server obj =  {}",formObj);

    const response = await axios({
        method: 'post',
        url: '/upload',
        data: formObj,
        headers:{
            'Content-Type': 'multipart/form-data',
        },
    });

    return response;


}

async function removeFile(uuid,fileName) {
    const response = await axios.delete(`/remove/${uuid}_${fileName}`)

    return response;

}