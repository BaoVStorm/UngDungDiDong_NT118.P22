import pandas as pd
import re


def convert_gdrive_url(url):
    if isinstance(url, str) and url.startswith("https://drive.google.com"):
        match = re.search(r'/d/([a-zA-Z0-9_-]+)', url)
        if match:
            file_id = match.group(1)
            return f'https://drive.google.com/uc?export=view&id={file_id}'
    return url  # Trả lại URL gốc nếu không phải link Drive hợp lệ

print(convert_gdrive_url("https://drive.google.com/file/d/10pp8-yIqb-lWHLk76hdWu-81foJ0NU3i/view?usp=drive_link"))