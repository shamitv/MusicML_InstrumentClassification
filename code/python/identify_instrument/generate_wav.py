from config import Conf
from data import get_filtered_songs, get_instrument_midi_for_song, get_instrument_wav_for_song
from audio_process import generate_wave_command
from pathlib import Path

conf = Conf()

song_id_list = get_filtered_songs()

print("GOt song list")

commands = []

for id in song_id_list:
    for i in conf.instruments:
        midi = get_instrument_midi_for_song(id, i)
        wav = get_instrument_wav_for_song(id, i)
        #print(midi + " " + wav)
        wav_path = Path(wav)
        wav_dir = wav_path.parent
        wav_dir.mkdir(parents=True, exist_ok=True)
        commands.append(generate_wave_command(midi, wav))

#print(commands)


bin_export_str = 'export CONVERT_BIN=' + '"' +conf.MUSESCORE_BIN+ '"'
mid_dir_export = 'export MIDI_DIR=' + conf.GENERATED_SONGS
wav_dir_export = 'export WAV_DIR=' + conf.GENERATED_SONGS_WAV

export_list = [bin_export_str , mid_dir_export , wav_dir_export, " ", "  "]

command_str = "\n".join(commands)

command_str = command_str.replace('"' +conf.MUSESCORE_BIN+ '"', '{CONVERT_BIN}')
command_str = command_str.replace(conf.GENERATED_SONGS_WAV , '{$WAV_DIR}')
command_str = command_str.replace(conf.GENERATED_SONGS , '{$MIDI_DIR}')

export_str = "\n".join(export_list)

print_str = export_str + "\n"+command_str


with open(conf.WAV_CONV_SHELL_SCRIPT, "w") as shell_script:
    shell_script.write(print_str)